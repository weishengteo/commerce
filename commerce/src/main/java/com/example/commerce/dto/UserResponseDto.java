package com.example.commerce.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserResponseDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
    private String username;
    private String email;
    private String role;
}
