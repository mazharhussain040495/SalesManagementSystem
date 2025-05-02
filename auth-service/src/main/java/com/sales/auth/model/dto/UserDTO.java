package com.sales.auth.model.dto;

public record UserDTO (
     String name,
     String phone,
     String email,
     String password,
     String address
){}
