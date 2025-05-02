package com.sales.order.service;

import com.sales.order.model.dto.OrderRequestDTO;
import com.sales.order.model.dto.OrderResponseDTO;
import com.sales.order.model.entity.Order;
import com.sales.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * Place a new order and store it in the database.
     * The created order is also cached.
     */
    @CachePut(value = "orders",
            condition = "#orderRequest != null and #orderRequest.id() != null",
            key = "#orderRequest.id")
    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequest) {
        Order order = new Order();
        order.setProductName(orderRequest.productName());
        order.setQuantity(orderRequest.quantity());
        order.setPrice(orderRequest.price());
        order.setCustomerEmail(orderRequest.customerEmail());

        Order savedOrder = orderRepository.save(order);
        return mapToResponseDTO(savedOrder);
    }

    /**
     * Fetch an order by its ID from the database.
     * This method first checks the Redis cache before hitting the database.
     */
    @Cacheable(value = "orders",condition = "#id != null", key = "#id")
    public Optional<OrderResponseDTO> getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::mapToResponseDTO);
    }

    /**
     * Get all orders placed by a customer, identified by their email.
     * Caching is not done for this method as customer-specific data could be updated often.
     */
    public List<OrderResponseDTO> getOrdersByCustomer(String email) {
        return orderRepository.findByCustomerEmail(email)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Fetch all orders from the database.
     * Caching is not done here as it may return a large set of orders that may change frequently.
     */
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Delete an order by its ID.
     * The cache for the specific order is evicted after deletion.
     */
    @CacheEvict(value = "orders",condition = "#id != null",key = "#id")
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new NoSuchElementException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    /**
     * Update an existing order by ID.
     * If the order exists, it is updated and cache is refreshed.
     */
    @CachePut(value = "orders", condition = "#id != null", key = "#id")
    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO orderRequest) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order not found with id: " + id));

        existingOrder.setProductName(orderRequest.productName());
        existingOrder.setQuantity(orderRequest.quantity());
        existingOrder.setPrice(orderRequest.price());
        existingOrder.setCustomerEmail(orderRequest.customerEmail());

        Order updatedOrder = orderRepository.save(existingOrder);
        return mapToResponseDTO(updatedOrder);
    }


    /**
     * Map an Order entity to an OrderResponseDTO.
     */
    private OrderResponseDTO mapToResponseDTO(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getOrderDate(),
                order.getCustomerEmail()
        );
    }
}
