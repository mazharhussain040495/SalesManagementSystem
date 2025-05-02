package com.sales.auth.model.dto;


public record UserResponseDTO (
     Long id,
     String name,
     String email,
     String phone,
     String address,
     String password
){}
