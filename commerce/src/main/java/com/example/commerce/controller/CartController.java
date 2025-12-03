package com.example.commerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.commerce.dto.CartDto;
import com.example.commerce.dto.CartResponseDto;
import com.example.commerce.service.CartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {
	
	private final CartService cartService;
	
	@PostMapping
	public ResponseEntity<CartResponseDto> createCart(@RequestBody CartDto request) {
		CartResponseDto cart = cartService.createCart(request.getUserId());
		return ResponseEntity.ok(cart);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<CartResponseDto> getCart(@PathVariable Long userId) {
		CartResponseDto cart = cartService.getCart(userId);
		return ResponseEntity.ok(cart);
	}
	
	@PostMapping("/item")
	public ResponseEntity<CartResponseDto> addItem(@RequestBody CartDto request) {
		CartResponseDto cart = cartService.addItem(request.getUserId(), request.getProductId(), request.getQuantity());
		return ResponseEntity.ok(cart);
	}
	
	@PutMapping("/item")
	public ResponseEntity<CartResponseDto> updateQuantity(@RequestBody CartDto request) {
		CartResponseDto cart = cartService.updateItemQuantity(request.getUserId(), request.getProductId(), request.getQuantity());
		return ResponseEntity.ok(cart);
	}
	
	@DeleteMapping("/item")
	public ResponseEntity<CartResponseDto> removeItem(@RequestBody CartDto request) {
		CartResponseDto cart = cartService.removeItem(request.getUserId(), request.getProductId());
		return ResponseEntity.ok(cart);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
		cartService.clearCart(userId);
		return ResponseEntity.noContent().build();
	}
}
