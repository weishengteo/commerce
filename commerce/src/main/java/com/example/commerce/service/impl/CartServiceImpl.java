package com.example.commerce.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.commerce.dto.CartResponseDto;
import com.example.commerce.entity.Cart;
import com.example.commerce.entity.CartItem;
import com.example.commerce.entity.Product;
import com.example.commerce.mapper.CartMapper;
import com.example.commerce.repository.CartRepository;
import com.example.commerce.repository.ProductRepository;
import com.example.commerce.repository.UserRepository;
import com.example.commerce.service.CartService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;
	
	@Override
	@CachePut(value = "carts", key = "#userId")
	public CartResponseDto createCart(Long userId) {
		if (userId == null) {
			throw new IllegalArgumentException("Invalid userId");
		}
		
		Optional<Cart> existing = cartRepository.findByUserIdAndActiveTrue(userId);
		
		if (existing.isPresent()) {
			return CartMapper.toDto(existing.get());
		}
		
		Cart cart = new Cart();
		cart.setUser(userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found")));
		cart.setActive(true);
		cart.setItems(new ArrayList<>());
		
		cartRepository.save(cart);
		
		return CartMapper.toDto(cart);
	}
	
	@Override
	@Cacheable(value = "carts", key = "#userId")
	public CartResponseDto getCart(Long userId) {
		if (userId == null) {
			throw new IllegalArgumentException("Invalid userId");
		}
		
		Cart cart = cartRepository.findByUserIdAndActiveTrue(userId)
				.orElseThrow(() -> new IllegalArgumentException("Cart not found"));
		
		return CartMapper.toDto(cart);
	}

	@Override
	@CachePut(value = "carts", key = "#userId")
	public CartResponseDto addItem(Long userId, Long productId, int quantity) {
		Cart cart = cartRepository.findByUserIdAndActiveTrue(userId)
				.orElseThrow(() -> new IllegalArgumentException("Cart not found"));
	
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new IllegalArgumentException("Product not found"));
		
		CartItem existingItem = cart.getItems().stream()
				.filter(i -> i.getProduct().getId().equals(productId))
				.findFirst()
				.orElse(null);
		
		if (existingItem != null) {
			existingItem.setQuantity(existingItem.getQuantity() + quantity);
		} else {
			CartItem item = new CartItem();
			item.setCart(cart);
			item.setProduct(product);
			item.setQuantity(quantity);
			
			cart.getItems().add(item);
		}
		
		cartRepository.save(cart);
		
		return CartMapper.toDto(cart);
	}

	@Override
	@CachePut(value = "carts", key = "#userId")
	public CartResponseDto updateItemQuantity(Long userId, Long productId, int quantity) {
		Cart cart = cartRepository.findByUserIdAndActiveTrue(userId)
				.orElseThrow(() -> new IllegalArgumentException("Cart not found"));
		
		CartItem existingItem = cart.getItems().stream()
				.filter(i -> i.getProduct().getId().equals(productId))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Item not found in cart"));
		
		if (quantity <= 0) {
			cart.getItems().remove(existingItem);
		} else {
			existingItem.setQuantity(quantity);
		}
		
		cartRepository.save(cart);
		
		return CartMapper.toDto(cart);
	}

	@Override
	@CachePut(value = "carts", key = "#userId")
	public CartResponseDto removeItem(Long userId, Long productId) {
		Cart cart = cartRepository.findByUserIdAndActiveTrue(userId)
				.orElseThrow(() -> new IllegalArgumentException("Cart not found"));
		
		CartItem existingItem = cart.getItems().stream()
				.filter(i -> i.getProduct().getId().equals(productId))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Item not found in cart"));
		
		cart.getItems().remove(existingItem);
		
		cartRepository.save(cart);
		
		return CartMapper.toDto(cart);
	}

	@Override
	@CacheEvict(value = "carts", key = "#userId")
	public void clearCart(Long userId) {
		Cart cart = cartRepository.findByUserIdAndActiveTrue(userId)
				.orElseThrow(() -> new IllegalArgumentException("Cart not found"));
		
		cart.getItems().clear();
		cartRepository.save(cart);
	}
}
