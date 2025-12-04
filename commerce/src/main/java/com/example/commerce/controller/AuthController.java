package com.example.commerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.commerce.dto.AuthDto;
import com.example.commerce.entity.User;
import com.example.commerce.repository.UserRepository;
import com.example.commerce.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthenticationManager authManager;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody AuthDto request) {
		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			return ResponseEntity.badRequest().body("Email already exists");
		}
		
		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(User.Role.USER);
		userRepository.save(user);
		return ResponseEntity.ok("User registered successfully");
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthDto request) {
		try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            String token = jwtUtil.generateJwtToken(request.getEmail());
            return ResponseEntity.ok("{\"token\":\"" + token + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
	}
	
	@PostMapping("/resetPassword")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> resetPassword(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AuthDto request) {
		User user = userRepository.findByEmail(userDetails.getUsername())
				.orElseThrow(() -> new IllegalArgumentException());
		
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		userRepository.save(user);
		
		return ResponseEntity.ok("Password reset successfully");
	}
	
}
