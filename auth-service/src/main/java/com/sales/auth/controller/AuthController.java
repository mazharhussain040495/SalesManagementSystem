package com.sales.auth.controller;

import com.sales.auth.client.UserClient;
import com.sales.auth.model.dto.AuthRequestDTO;
import com.sales.auth.model.dto.AuthResponseDTO;
import com.sales.auth.model.dto.UserDTO;
import com.sales.auth.model.dto.UserResponseDTO;
import com.sales.common.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication API", description = "Endpoints for user authentication and registration")
public class AuthController {

    private final UserClient userClient;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error or bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserDTO user) {
        UserResponseDTO createdUser = userClient.registerUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @Operation(summary = "Authenticate a user and get JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authenticated successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(@Valid @RequestBody AuthRequestDTO loginRequest) {
        try {
            UserResponseDTO user = userClient.getUserByEmail(loginRequest.email());

            if (passwordEncoder.matches(loginRequest.password(), user.password())) {
                String token = jwtUtil.generateToken(user.email());
                return ResponseEntity.ok(new AuthResponseDTO(token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new AuthResponseDTO("Invalid email or password"));
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponseDTO("Authentication failed: " + ex.getMessage()));
        }
    }
}
