package com.sales.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @Schema(description = "User ID", example = "1", hidden = true)
        Long id,

        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
        @Schema(description = "Full name of the user", example = "John Doe")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Schema(description = "Email address of the user", example = "johndoe@example.com")
        String email,

        @NotBlank(message = "Phone is required")
        @Size(min = 10, max = 15, message = "Phone must be between 10 and 15 digits")
        @Schema(description = "Phone number of the user", example = "1234567890")
        String phone,

        @NotBlank(message = "Address is required")
        @Size(max = 255, message = "Address must be less than 255 characters")
        @Schema(description = "Address of the user", example = "123 Main St, Springfield")
        String address,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        @Schema(description = "Password for user authentication", example = "securepassword123")
        String password
) {}
