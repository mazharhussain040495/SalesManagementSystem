package com.sales.order.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name must not be blank")
    private String productName;

    @NotNull(message = "Quantity must not be null")
    private int quantity;

    @NotNull(message = "Price must not be null")
    private double price;

    private LocalDateTime orderDate;

    @NotBlank(message = "Customer email must not be blank")
    private String customerEmail;

    @PrePersist
    public void prePersist() {
        // Set the order date to the current time when the entity is saved to the database
        this.orderDate = LocalDateTime.now();
    }

    // Getters and Setters are automatically provided by Lombok's @Data annotation
}
