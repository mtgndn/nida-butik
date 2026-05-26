package org.example.taskmanager.dto;

import org.example.taskmanager.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        String customerEmail,
        OrderStatus status,
        BigDecimal totalAmount,
        LocalDateTime createdAt
) {
}
