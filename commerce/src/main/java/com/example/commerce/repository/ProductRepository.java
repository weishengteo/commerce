package com.example.commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.commerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	List<Product> findByNameContaining(String keyword);
}
