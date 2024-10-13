package com.example.microservicesmongodb.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.username}")
    private String username;

    @Value("${spring.data.mongodb.password}")
    private String password;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.pool.max-size}")
    private int maxSize;

    @Value("${spring.data.mongodb.pool.min-size}")
    private int minSize;

    @Value("${spring.data.mongodb.pool.max-wait-time}")
    private long maxWaitTime;

    @Value("${spring.data.mongodb.pool.max-idle-time}")
    private long maxIdleTime;

    @Value("${spring.data.mongodb.socket.connect-timeout:10000}")
    private long connectTimeout;

    @Value("${spring.data.mongodb.socket.read-timeout:15000}")
    private long readTimeout;

    @Value("${spring.data.mongodb.retryWrites:true}")
    private boolean retryWrites;

    @Value("${spring.data.mongodb.w:majority}")
    private String writeConcern;

    @Value("${spring.data.mongodb.appName:Cluster0}")
    private String appName;

    @Bean
    public MongoClient mongoClient() {
        // Build connection string with username, password, and other options
        String connectionUri = String.format(
                "mongodb+srv://%s:%s@%s/%s?retryWrites=%b&w=%s&appName=%s",
                username, password, host, database, retryWrites, writeConcern, appName
        );

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionUri))
                .applyToConnectionPoolSettings(builder -> builder
                        .maxSize(maxSize)
                        .minSize(minSize)
                        .maxWaitTime(maxWaitTime, TimeUnit.MILLISECONDS)
                        .maxConnectionIdleTime(maxIdleTime, TimeUnit.MILLISECONDS)
                )
                .applyToSocketSettings(socketBuilder -> socketBuilder
                        .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                        .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                )
                .retryWrites(retryWrites)
                .build();

        return MongoClients.create(settings);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), database);
    }
}
