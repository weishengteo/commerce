package com.example.commerce.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemResponseDto implements Serializable{
	private static final long serialVersionUID = 1L;

    private Long id;
    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal price;
}
