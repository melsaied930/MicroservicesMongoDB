package com.example.microservicesmongodb.repository;

import com.example.microservicesmongodb.records.RestockOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface RestockOrderRepository extends MongoRepository<RestockOrder, UUID> {
    // Custom query methods (if needed)
    List<RestockOrder> findByProductId(UUID productId);
}