package com.example.commerce.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductResponseDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
}
