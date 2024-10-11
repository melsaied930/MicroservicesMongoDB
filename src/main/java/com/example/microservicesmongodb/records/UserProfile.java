package com.example.microservicesmongodb.records;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Document(collection = "user_profiles")
public record UserProfile(
        @Id UUID id,
        UUID userId, // Reference to users._id
        String firstName,
        String lastName,
        UUID addressId, // Reference to addresses._id
        String phoneNumber,
        UUID shippingAddressId // Reference to addresses._id
) {}