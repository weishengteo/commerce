package com.example.commerce.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CartResponseDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
    private boolean active;
    private LocalDateTime createdDate;
    private UserResponseDto user;
    private List<CartItemResponseDto> items = new ArrayList<>();
}
