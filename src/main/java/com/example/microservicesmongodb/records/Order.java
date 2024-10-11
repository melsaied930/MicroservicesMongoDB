package com.example.microservicesmongodb.records;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Document(collection = "orders")
public record Order(
        @Id UUID id,
        UUID userId, // Reference to users._id
        String status, // E.g., "pending", "shipped", "delivered"
        List<OrderItem> items,
        BigDecimal totalPrice,
        UUID shippingAddressId, // Reference to addresses._id
        Instant createdAt,
        Instant updatedAt
) {}

record OrderItem(
        UUID productId, // Reference to products._id
        int quantity,
        BigDecimal price // Price when the order was placed
) {}