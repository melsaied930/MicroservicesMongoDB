package com.example.microservicesmongodb.repository;

import com.example.microservicesmongodb.records.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "carts", path = "carts")
public interface CartRepository extends MongoRepository<Cart, UUID> {
    // Custom query methods if needed
    Optional<Cart> findByUserId(UUID userId);
}
