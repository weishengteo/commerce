package com.example.commerce.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.commerce.dto.ProductDto;
import com.example.commerce.dto.ProductResponseDto;

public interface ProductService {
	public ProductResponseDto createProduct(ProductDto request);
	public ProductResponseDto updateProduct(ProductDto request);
	public void deleteProduct(Long productId);
	
	public Page<ProductResponseDto> getAllProducts(int pageNumber, int pageSize);
	public List<ProductResponseDto> searchProducts(String keyword);
}
