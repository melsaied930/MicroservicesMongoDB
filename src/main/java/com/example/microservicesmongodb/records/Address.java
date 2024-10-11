package com.example.microservicesmongodb.records;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Document(collection = "addresses")
public record Address(
        @Id UUID id,
        String street,
        String city,
        String state,
        String postalCode,
        String country
) {}