package com.example.commerce.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class OrderResponseDto implements Serializable{
	private static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdDate;

    private List<OrderItemResponseDto> items;
}
