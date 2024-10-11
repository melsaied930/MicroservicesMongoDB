package com.example.microservicesmongodb.repository;

import com.example.microservicesmongodb.records.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface SupplierRepository extends MongoRepository<Supplier, UUID> {
    // Custom query methods (if needed)
    Optional<Supplier> findByName(String name);
}