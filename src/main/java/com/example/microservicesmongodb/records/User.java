package com.example.microservicesmongodb.records;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;
import java.time.Instant;
import java.util.List;

@Document(collection = "users")
public record User(
        @Id UUID id,
        String username,
        String email,
        String password, // should be hashed
        List<String> roles, // E.g., ['customer', 'admin']
        UUID profileId, // Reference to user_profiles._id
        Instant createdAt,
        Instant updatedAt
) {}