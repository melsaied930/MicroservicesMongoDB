package com.example.microservicesmongodb.records;

import org.bson.types.ObjectId;
import java.time.LocalDateTime;

public record Comment(
        ObjectId _id,
        String name,
        String email,
        ObjectId movie_id,
        String text,
        LocalDateTime date
) {}
