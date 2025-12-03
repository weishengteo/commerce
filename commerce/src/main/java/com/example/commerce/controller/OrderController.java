package com.example.commerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.commerce.dto.OrderDto;
import com.example.commerce.dto.OrderResponseDto;
import com.example.commerce.service.OrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
	
	private final OrderService orderService;
	
	@PostMapping("/checkout")
	public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderDto request) {
		OrderResponseDto order = orderService.createOrder(request.getUserId());
		return ResponseEntity.ok(order);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
		OrderResponseDto order = orderService.getOrderById(id);
		return ResponseEntity.ok(order);
	}
	
	@PostMapping("/user")
	public ResponseEntity<List<OrderResponseDto>> getUserOrders(@RequestBody Long userId) {
		List<OrderResponseDto> order = orderService.getUserOrders(userId);
		return ResponseEntity.ok(order);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateOrderStatus(@RequestBody OrderDto request) {
		orderService.updateOrderStatus(request.getOrderId(), request.getStatus());
		return ResponseEntity.noContent().build();
	}
	
}
