package com.example.microservicesmongodb;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Service;

import com.mongodb.client.*;
import com.mongodb.client.model.*;
import com.mongodb.client.result.UpdateResult;
import org.bson.conversions.Bson;

@Service
public class MongoDB_Transactions_Operations {

    private final MongoClient client;
    private final MongoCollection<Document> collection;

    public MongoDB_Transactions_Operations(MongoClient client, MongoCollection<Document> moviesCollection) {
        this.client = client;
        this.collection = moviesCollection;
    }

    public void start() {
        withTransaction();
    }

    private void withTransaction() {
        System.out.println("\n----- MongoDB Transaction Match and Group -----");

        // Insert a new document
        collection.insertOne(new Document().append("title", "Deletable Movie 1").append("year", 2025).append("type", "temp"));

        System.out.println("Before Transaction -----");
        // Query and projection to find the inserted document
        Bson query = Filters.eq("title", "Deletable Movie 1");
        Document document = collection.find(query).first();
        System.out.println(document);


        ClientSession session = client.startSession();              // (1) Start a client session for the transaction

        TransactionBody<String> transaction = () -> {               // (2) Define the transaction logic using a TransactionBody
            Bson update = Updates.set("type", "film");              // (3) Update the document's type within the session
            UpdateResult one = collection.updateOne(session, query, update);

            // Print the result of the update operation
            System.out.printf("UpdateOneResult: Matched = %d, Modified = %d, Acknowledged = %b\n", one.getMatchedCount(), one.getModifiedCount(), one.wasAcknowledged());

            // Simulate a failure to demonstrate transaction rollback
            if (true) throw new RuntimeException("Transaction failed!");

            // Return success message
            return "Transaction completed successfully.";
        };

        try {
            String result = session.withTransaction(transaction);   // (4) Execute the transaction
            System.out.printf("\033[32m%s\033[0m\n",result);
        } catch (RuntimeException e) {
            // Handle transaction failure
            System.out.printf("\033[0;31m%s\033[0m\n", e.getMessage());
        } finally {
            session.close();                                        // (5) Close the session
        }

        System.out.println("After Transaction -----");
        // Verify the document after the transaction (outside the session)
        document = collection.find(query).first();
        System.out.println(document);

        // Clean up: Delete the test document
        System.out.println(collection.deleteOne(query));
    }
}