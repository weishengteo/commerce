package com.example.commerce.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.commerce.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
	
	@Query("""
	        SELECT c FROM Cart c
	        JOIN FETCH c.user
	        LEFT JOIN FETCH c.items i
	        LEFT JOIN FETCH i.product
	        WHERE c.user.id = :userId AND c.active = true
	""")
	public Optional<Cart> findByUserIdAndActiveTrue(Long userId);
	
	public List<Cart> findByActiveTrueAndCreatedDateBefore(LocalDateTime date);
}
