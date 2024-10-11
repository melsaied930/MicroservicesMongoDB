package com.example.microservicesmongodb.records;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Document(collection = "carts")
public record Cart(
        @Id UUID id,
        UUID userId, // Reference to users._id
        List<CartItem> items,
        BigDecimal totalPrice,
        Instant createdAt,
        Instant updatedAt
) {}

record CartItem(
        UUID productId, // Reference to products._id
        int quantity,
        BigDecimal price // Price at the time of adding to cart
) {}
