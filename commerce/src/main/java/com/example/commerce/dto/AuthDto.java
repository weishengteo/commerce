package com.example.commerce.dto;

import com.example.commerce.entity.User;

import lombok.Data;

@Data
public class AuthDto {
	private String username;
	private String email;
	private String password;
	private User.Role role;
}
