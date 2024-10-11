package com.example.microservicesmongodb.repository;

import com.example.microservicesmongodb.records.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {
    // Custom query methods (if needed)
    Optional<User> findByEmail(String email);
}