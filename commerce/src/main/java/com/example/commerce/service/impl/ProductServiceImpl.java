package com.example.commerce.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.commerce.dto.ProductDto;
import com.example.commerce.dto.ProductResponseDto;
import com.example.commerce.entity.Product;
import com.example.commerce.mapper.ProductMapper;
import com.example.commerce.repository.ProductRepository;
import com.example.commerce.service.ProductService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepository productRepository;
	
	@Override
	public ProductResponseDto createProduct(ProductDto request) {
		if (request == null || request.getName() == null) {
			throw new IllegalArgumentException("Invalid request");
		}
		
		Product product = new Product();
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setStockQuantity(request.getStockQuantity());
		
		return ProductMapper.toDto(productRepository.save(product));
	}

	@Override
	public ProductResponseDto updateProduct(ProductDto request) {
		if (request == null || request.getName() == null) {
			throw new IllegalArgumentException("Invalid request");
		}
		
		if (request.getId() == null) {
			throw new IllegalArgumentException("Product ID is required");
		}
		
		Product product = productRepository.findById(request.getId())
				.orElseThrow(() -> new IllegalArgumentException("Product not found"));
		
		if (request.getName() != null)       product.setName(request.getName());
	    if (request.getDescription() != null) product.setDescription(request.getDescription());
	    if (request.getPrice() != null)       product.setPrice(request.getPrice());
	    if (request.getStockQuantity() != null) product.setStockQuantity(request.getStockQuantity());
	    
		return ProductMapper.toDto(productRepository.save(product));
	}

	@Override
	public void deleteProduct(Long productId) {
		if (productId == null) {
			throw new IllegalArgumentException("Product ID is required");
		}
		
		if (!productRepository.existsById(productId)) {
			throw new IllegalArgumentException("Product not found"); 
		}
		
		productRepository.deleteById(productId);
	}

	@Override
	public Page<ProductResponseDto> getAllProducts(int pageNumber, int pageSize) {
	    Pageable pageable = PageRequest.of(pageNumber, pageSize);
	    Page<Product> productsPage = productRepository.findAll(pageable);

	    return productsPage.map(ProductMapper::toDto);
	}

	@Override
	public List<ProductResponseDto> searchProducts(String keyword) {
		if (keyword == null || keyword.isEmpty()) {
			throw new IllegalArgumentException("Invalid keyword");
		}
		
		List<Product> products = productRepository.findByNameContaining(keyword);
		
		return products.stream()
				.map(ProductMapper::toDto)
				.toList();
	}

}
