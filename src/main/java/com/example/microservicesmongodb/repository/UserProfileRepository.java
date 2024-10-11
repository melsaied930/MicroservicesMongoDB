package com.example.microservicesmongodb.repository;

import com.example.microservicesmongodb.records.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface UserProfileRepository extends MongoRepository<UserProfile, UUID> {
    // Additional custom queries can be defined here if needed
}
