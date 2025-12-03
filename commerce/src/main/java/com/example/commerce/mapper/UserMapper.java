package com.example.commerce.mapper;

import com.example.commerce.dto.UserResponseDto;
import com.example.commerce.entity.User;

import lombok.Data;

@Data
public class UserMapper {
	
	public static UserResponseDto toDto(User user) {
		UserResponseDto response = new UserResponseDto();
		
		response.setId(user.getId());
		response.setEmail(user.getEmail());
		response.setRole(user.getRole().name());
		response.setUsername(user.getUsername());
		return response;
	}
}
