package com.example.microservicesmongodb.records;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;
import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "products")
public record Product(
        @Id UUID id,
        String name,
        String description,
        UUID categoryId, // Reference to categories._id
        BigDecimal price,
        int stock,
        String imageUrl,
        Instant createdAt,
        Instant updatedAt
) {}