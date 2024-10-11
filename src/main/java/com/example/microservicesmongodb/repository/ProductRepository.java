package com.example.microservicesmongodb.repository;

import com.example.microservicesmongodb.records.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.UUID;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, UUID> {
    // Custom query using aggregation to lookup category by name
    @Query("{ 'categoryId': ?0 }")
    List<Product> findByCategoryId(UUID categoryId);
}
