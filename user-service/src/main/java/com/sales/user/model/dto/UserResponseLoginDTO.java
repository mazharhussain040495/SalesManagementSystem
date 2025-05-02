package com.sales.user.model.dto;

public record UserResponseLoginDTO(
        Long id,
        String name,
        String email,
        String phone,
        String address,
        String password
) {}
