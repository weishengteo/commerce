package com.example.commerce.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.commerce.dto.OrderResponseDto;
import com.example.commerce.entity.Cart;
import com.example.commerce.entity.Order;
import com.example.commerce.entity.OrderItem;
import com.example.commerce.entity.OrderStatus;
import com.example.commerce.entity.Product;
import com.example.commerce.exception.OutOfStockException;
import com.example.commerce.mapper.OrderMapper;
import com.example.commerce.repository.CartRepository;
import com.example.commerce.repository.OrderRepository;
import com.example.commerce.repository.ProductRepository;
import com.example.commerce.service.OrderService;
import com.example.commerce.util.OrderStatusHelper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
	
	private final OrderRepository orderRepository;
	private final CartRepository cartRepository;
	private final ProductRepository productRepository;

	@Override
	public OrderResponseDto createOrder(Long userId) {
		if (userId == null) {
			throw new IllegalArgumentException("Invalid userId");
		}
		
		Cart cart = cartRepository.findByUserIdAndActiveTrue(userId)
				.orElseThrow(() -> new IllegalArgumentException("Cart not found"));
		
		if (cart.getItems().isEmpty()) {
		    throw new IllegalStateException("Cannot create order from an empty cart");
		}
		
		Order order = new Order();
		order.setUser(cart.getUser());
		order.setCreatedDate(LocalDateTime.now());
		order.setStatus(OrderStatus.PENDING);
		
		List<OrderItem> items = cart.getItems().stream().map(cartItem -> {
			if (cartItem.getProduct().getStockQuantity() < cartItem.getQuantity()) {
				throw new OutOfStockException("Not enough stock for product: " + cartItem.getProduct().getName());
			}
			
			OrderItem item = new OrderItem();
			item.setProduct(cartItem.getProduct());
			item.setOrder(order);
			item.setPrice(cartItem.getProduct().getPrice());
			item.setQuantity(cartItem.getQuantity());
			return item;
		}).collect(Collectors.toList());
		
		order.setItems(items);
		
		BigDecimal total = items.stream().map(item -> 
			item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
		).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		order.setTotalAmount(total);
		orderRepository.save(order);
		
		cart.getItems().clear();
		cart.setActive(false);
		cartRepository.save(cart);
		
		return OrderMapper.toDto(order);
	}

	@Override
	public OrderResponseDto processPayment(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new IllegalArgumentException("Order not found"));
		
		// Check and deduct stock
		for (OrderItem item : order.getItems()) {
	        Product product = item.getProduct();
	        if (product.getStockQuantity() < item.getQuantity()) {
	            throw new OutOfStockException("Not enough stock for product: " + product.getName());
	        }
	        // Deduct stock
	        product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
	        productRepository.save(product);
	    }
		
		order.setStatus(OrderStatus.PAID);
		return OrderMapper.toDto(orderRepository.save(order));
	}

	@Override
	public OrderResponseDto getOrderById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Invalid id");
		}
		
		return OrderMapper.toDto(orderRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Order not found")));
	}

	@Override
	public List<OrderResponseDto> getUserOrders(Long userId) {
		if (userId == null) {
			throw new IllegalArgumentException("Invalid userId");
		}
		
		List<Order> orders = orderRepository.findByUserId(userId);
		
		return orders.stream()
				.map(OrderMapper::toDto)
				.toList();
	}

	@Override
	public void updateOrderStatus(Long orderId, OrderStatus status) {
		if (orderId == null) {
			throw new IllegalArgumentException("Invalid orderId");
		}
		
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new IllegalArgumentException("Order not found"));
		
		if (!OrderStatusHelper.canTransition(order.getStatus(), status)) {
	        throw new IllegalArgumentException("Cannot transition to specified status");
	    }

	    order.setStatus(status);
	    orderRepository.save(order);
	}
}
