package com.sales.order.model.dto;

public record UserResponseDTO(
        Long id,
        String name,
        String phone,
        String email,
        String address
) {}
