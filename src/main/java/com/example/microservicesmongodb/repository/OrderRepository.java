package com.example.microservicesmongodb.repository;

import com.example.microservicesmongodb.records.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends MongoRepository<Order, UUID> {
    // Custom query methods (if needed)
    List<Order> findByUserId(UUID userId);
}