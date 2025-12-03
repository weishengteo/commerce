package com.example.commerce.dto;

import com.example.commerce.entity.OrderStatus;

import lombok.Data;

@Data
public class OrderDto {
	
	private Long userId;
	private Long orderId;
	private OrderStatus status;
}
