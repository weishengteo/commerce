package com.example.commerce.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CartItemResponseDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
    private ProductResponseDto product;
    private int quantity;
}
