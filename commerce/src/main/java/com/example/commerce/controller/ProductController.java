package com.example.commerce.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.commerce.dto.ProductDto;
import com.example.commerce.dto.ProductResponseDto;
import com.example.commerce.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
	
	private final ProductService productService;
	
	@PostMapping
	public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductDto request) {
		ProductResponseDto product = productService.createProduct(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(product);
	}
	
	@PutMapping
	public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody ProductDto request) {
		ProductResponseDto product = productService.updateProduct(request);
		return ResponseEntity.status(HttpStatus.OK).body(product);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<Page<ProductResponseDto>> getProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
		Page<ProductResponseDto> products = productService.getAllProducts(page, size);
		return ResponseEntity.ok(products);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<ProductResponseDto>> searchProducts(@RequestParam String keyword) {
		List<ProductResponseDto> products = productService.searchProducts(keyword);
		return ResponseEntity.ok(products);
	}
}
