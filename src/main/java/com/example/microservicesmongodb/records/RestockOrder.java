package com.example.microservicesmongodb.records;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;
import java.time.Instant;

@Document(collection = "restock_orders")
public record RestockOrder(
        @Id UUID id,
        UUID productId, // Reference to products._id
        UUID supplierId, // Reference to suppliers._id
        int quantity,
        String status, // E.g., "pending", "received"
        Instant restockDate,
        Instant createdAt,
        Instant updatedAt
) {}