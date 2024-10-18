package com.example.microservicesmongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUri);
    }

    @Bean
    public MongoDatabase mongoDatabase(MongoClient mongoClient) {
        return mongoClient.getDatabase(databaseName);
    }

    @Bean(name = "moviesCollection")
    public MongoCollection<Document> moviesCollection(MongoDatabase mongoDatabase) {
        return mongoDatabase.getCollection("movies");
    }

    @Bean(name = "commentsCollection")
    public MongoCollection<Document> commentsCollection(MongoDatabase mongoDatabase) {
        return mongoDatabase.getCollection("comments");
    }

    @Bean(name = "usersCollection")
    public MongoCollection<Document> usersCollection(MongoDatabase mongoDatabase) {
        return mongoDatabase.getCollection("users");
    }
}
