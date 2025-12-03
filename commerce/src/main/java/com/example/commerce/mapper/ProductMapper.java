package com.example.commerce.mapper;

import com.example.commerce.dto.ProductResponseDto;
import com.example.commerce.entity.Product;

public class ProductMapper {
	
	public static ProductResponseDto toDto(Product product) {
		ProductResponseDto response = new ProductResponseDto();
		
		response.setId(product.getId());
		response.setName(product.getName());
		response.setDescription(product.getDescription());
		response.setPrice(product.getPrice());
		response.setStockQuantity(product.getStockQuantity());
		
		return response;
	}
}
