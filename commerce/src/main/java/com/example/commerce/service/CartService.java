package com.example.commerce.service;

import com.example.commerce.dto.CartResponseDto;

public interface CartService {
	public CartResponseDto createCart(Long userId);
	public CartResponseDto getCart(Long userId);
	public CartResponseDto addItem(Long userId, Long productId, int quantity);
	public CartResponseDto updateItemQuantity(Long userId, Long productId, int quantity);
	public CartResponseDto removeItem(Long userId, Long productId);
	public void clearCart(Long userId);
}
