package com.example.commerce.mapper;

import java.util.stream.Collectors;

import com.example.commerce.dto.CartItemResponseDto;
import com.example.commerce.dto.CartResponseDto;
import com.example.commerce.entity.Cart;
import com.example.commerce.entity.CartItem;

public class CartMapper {
	
	public static CartResponseDto toDto(Cart cart) {
		CartResponseDto response = new CartResponseDto();
		
		response.setId(cart.getId());
		response.setCreatedDate(cart.getCreatedDate());
		response.setActive(cart.isActive());
		response.setUser(UserMapper.toDto(cart.getUser()));
		response.setItems(cart.getItems().stream()
				.map(CartMapper::toItemDto)
				.collect(Collectors.toList()));
		
		return response;
	}
	
	public static CartItemResponseDto toItemDto(CartItem item) {
		CartItemResponseDto response = new CartItemResponseDto();
		
		response.setId(item.getId());
		response.setProduct(ProductMapper.toDto(item.getProduct()));
		response.setQuantity(item.getQuantity());
		return response;
	}
}
