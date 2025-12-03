package com.example.commerce.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.commerce.entity.Cart;
import com.example.commerce.repository.CartRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CartScheduler {
	
	private final CartRepository cartRepository;
	
	@Scheduled(fixedRate = 300_000)
	@Transactional
	public void invalidateCarts() {
		LocalDateTime cutoff = LocalDateTime.now().minusMinutes(5);
		List<Cart> expiredCarts = cartRepository.findByActiveTrueAndCreatedDateBefore(cutoff);
		
		for (Cart cart: expiredCarts) {
			cart.setActive(false);
		}
		
		cartRepository.saveAll(expiredCarts);
	}
}
