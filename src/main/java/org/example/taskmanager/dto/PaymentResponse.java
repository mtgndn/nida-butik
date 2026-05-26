package org.example.taskmanager.dto;

import org.example.taskmanager.entity.PaymentStatus;

import java.math.BigDecimal;

public record PaymentResponse(
        Long id,
        Long orderId,
        PaymentStatus status,
        BigDecimal amount,
        String transactionCode
) {
}
