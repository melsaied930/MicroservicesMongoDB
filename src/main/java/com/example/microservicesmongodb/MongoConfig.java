package com.example.microservicesmongodb;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
public class MongoConfig {
    /*
    <dependency>
    <groupId>org.mongodb</groupId>
    <artifactId>mongodb-driver-sync</artifactId>
    <version>4.7.1</version>
    </dependency>
    */
    @Bean
    public MongoClient mongoClient() {
        String connectionString = "mongodb+srv://admin:admin@cluster0.xxkqp.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

        ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();

        // Define connection pool and socket settings
        MongoClientSettings settings = MongoClientSettings
                .builder()
                .applyConnectionString(new ConnectionString(connectionString))
//                .applyToSocketSettings(builder -> builder
//                        .connectTimeout(30000, java.util.concurrent.TimeUnit.MILLISECONDS))
//                .applyToConnectionPoolSettings(builder -> builder
//                        .maxSize(100)
//                        .minSize(10))
                .serverApi(serverApi).build();

        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase("database");
                database.runCommand(new Document("ping", 1));
                log.info("Pinged your deployment. Successfully connected to MongoDB!");
            } catch (MongoException e) {
                log.error("An error occurred while trying to connect to the MongoDB database", e);
            }
        } catch (Exception e) {
            log.error("An unexpected error occurred", e);
        }

        return MongoClients.create(settings);
    }
}
