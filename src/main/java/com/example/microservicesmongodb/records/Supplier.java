package com.example.microservicesmongodb.records;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Document(collection = "suppliers")
public record Supplier(
        @Id UUID id,
        String name,
        ContactPerson contactPerson,
        UUID addressId, // Reference to addresses._id
        Instant createdAt,
        Instant updatedAt
) {}

record ContactPerson(
        String name,
        String email,
        String phoneNumber
) {}