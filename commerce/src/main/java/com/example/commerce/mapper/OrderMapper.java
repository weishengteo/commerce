package com.example.commerce.mapper;

import java.util.stream.Collectors;

import com.example.commerce.dto.OrderItemResponseDto;
import com.example.commerce.dto.OrderResponseDto;
import com.example.commerce.entity.Order;
import com.example.commerce.entity.OrderItem;

public class OrderMapper {

    public static OrderResponseDto toDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus().name());
        dto.setCreatedDate(order.getCreatedDate());

        dto.setItems(
            order.getItems().stream()
                .map(OrderMapper::toItemDto)
                .collect(Collectors.toList())
        );

        return dto;
    }

    private static OrderItemResponseDto toItemDto(OrderItem item) {
        OrderItemResponseDto dto = new OrderItemResponseDto();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        return dto;
    }
}
