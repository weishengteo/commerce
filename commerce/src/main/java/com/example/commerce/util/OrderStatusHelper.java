package com.example.commerce.util;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Set;

import com.example.commerce.entity.OrderStatus;

public class OrderStatusHelper {
	// Map of allowed transitions
    private static final EnumMap<OrderStatus, Set<OrderStatus>> transitions = new EnumMap<>(OrderStatus.class);

    static {
        transitions.put(OrderStatus.PENDING, EnumSet.of(OrderStatus.PAID, OrderStatus.CANCELLED));
        transitions.put(OrderStatus.PAID, EnumSet.of(OrderStatus.SHIPPED, OrderStatus.CANCELLED));
        transitions.put(OrderStatus.SHIPPED, EnumSet.of(OrderStatus.CANCELLED));
        transitions.put(OrderStatus.CANCELLED, EnumSet.noneOf(OrderStatus.class));
    }

    // Check if transition is allowed
    public static boolean canTransition(OrderStatus current, OrderStatus next) {
        return transitions.getOrDefault(current, EnumSet.noneOf(OrderStatus.class)).contains(next);
    }
}
