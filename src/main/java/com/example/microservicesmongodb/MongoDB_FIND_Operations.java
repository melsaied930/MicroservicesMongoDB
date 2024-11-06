package com.example.microservicesmongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
 import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.elemMatch;
import static com.mongodb.client.model.Filters.eq;

@Service
public class MongoDB_FIND_Operations {

    private final MongoCollection<Document> collection;
    private BsonValue id;

    public MongoDB_FIND_Operations(MongoCollection<Document> moviesCollection) {
        this.collection = moviesCollection;
    }

    public void start() {
        find();
        findElemMatch();
    }

    private void findElemMatch() {
        System.out.println("\n----- Find elemMatch Documents in Collection -----");
        // Query using Filters.elemMatch for an array field
//        Bson query = elemMatch("products", eq("InvestmentStock"));

        // Execute the query and print the results
//        collection.find(query).forEach(doc -> System.out.println(doc.toJson()));
    }

    private void find() {
        System.out.println("\n----- Find Documents in Collection -----");

        Bson query = eq("cast", "Alex Rocco");

        Bson project = Projections.fields(
                Projections.include("title", "year", "cast"),
                Projections.excludeId());

        FindIterable<Document> findIterable = collection.find(query).projection(project);
        for (Document document : findIterable) {
            System.out.println(document != null ? document : "No document found.");
        }
    }
}
