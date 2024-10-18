package com.example.microservicesmongodb;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    private final MongoCollection<Document> moviesCollection;
    private final MongoCollection<Document> commentsCollection;

    public ExampleService(
            MongoCollection<Document> moviesCollection,
            MongoCollection<Document> commentsCollection) {
        this.moviesCollection = moviesCollection;
        this.commentsCollection = commentsCollection;
    }

    public void queryMovies() {
        Document movie = moviesCollection.find().first();
        System.out.println("First Movie: " + movie);
    }

    public void queryComments() {
        Document comment = commentsCollection.find().first();
        System.out.println("First Comment: " + comment);
    }
}
