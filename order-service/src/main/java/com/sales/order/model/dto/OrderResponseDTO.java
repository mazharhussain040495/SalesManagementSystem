package com.sales.order.model.dto;

import java.time.LocalDateTime;

public record OrderResponseDTO(
        Long id,
        String productName,
        int quantity,
        double price,
        String orderDate,
        String customerEmail
) {}
