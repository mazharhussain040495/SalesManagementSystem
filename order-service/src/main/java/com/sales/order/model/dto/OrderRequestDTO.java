package com.sales.order.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderRequestDTO(
        @NotBlank(message = "Product name is required")
        String productName,

        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity,

        @Min(value = 0, message = "Price must be non-negative")
        double price,

        @Email(message = "Invalid email format")
        @NotBlank(message = "Customer email is required")
        String customerEmail
) {}
