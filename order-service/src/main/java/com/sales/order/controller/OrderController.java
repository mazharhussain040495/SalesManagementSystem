package com.sales.order.controller;

import com.sales.order.model.dto.OrderRequestDTO;
import com.sales.order.model.dto.OrderResponseDTO;
import com.sales.order.service.OrderService;
import com.sales.order.exception.OrderNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order API", description = "Operations related to sales orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Place a new order")
    @PostMapping("/")
    public ResponseEntity<OrderResponseDTO> placeOrder(@Valid @RequestBody OrderRequestDTO orderRequest) {
        return ResponseEntity.ok(orderService.placeOrder(orderRequest));
    }

    @Operation(summary = "Get order by ID")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable("id") Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
    }

    @Operation(summary = "Get all orders for a customer by email")
    @GetMapping("/customer/{email}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByCustomer(@PathVariable("email") String email) {
        List<OrderResponseDTO> orders = orderService.getOrdersByCustomer(email);
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found for customer with email: " + email);
        }
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Update an existing order")
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrder(
            @PathVariable("id") Long id,
            @Valid @RequestBody OrderRequestDTO orderRequest) {
        return ResponseEntity.ok(orderService.updateOrder(id, orderRequest));
    }

    @Operation(summary = "Get all orders")
    @GetMapping("/")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @Operation(summary = "Delete order by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
}
