package com.example.microservicesmongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    private final MongoCollection<Document> collection;

    public ExampleService(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public void start() {
        insertSampleDocument();
    }

    public void insertSampleDocument() {
        Bson query = Filters.eq("year", "2014");
        Bson projection = Projections.fields(
                Projections.include("title", "released", "year", "type"),
                Projections.excludeId());
        Document document = collection.
                find(query)
                .projection(projection)
                .first();
        System.out.println(document);
    }
}
