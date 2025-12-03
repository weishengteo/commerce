package com.example.commerce.dto;

import lombok.Data;

@Data
public class CartDto {
	
	private Long userId;
	
	private Long productId;
	
	private int quantity;
}
