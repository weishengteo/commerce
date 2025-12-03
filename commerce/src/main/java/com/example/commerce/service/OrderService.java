package com.example.commerce.service;

import java.util.List;

import com.example.commerce.dto.OrderResponseDto;
import com.example.commerce.entity.OrderStatus;

public interface OrderService {
	
	public OrderResponseDto createOrder(Long userId);
	
	public OrderResponseDto getOrderById(Long id);
	
	public List<OrderResponseDto> getUserOrders(Long userId);
	
	public void updateOrderStatus(Long orderId, OrderStatus status);
}
