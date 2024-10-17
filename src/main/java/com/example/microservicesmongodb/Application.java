package com.example.microservicesmongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {

    private static BsonValue id;

    public static void main(String[] args) {
        MongoClient client = createMongoClient("mongodb+srv://admin:admin@cluster0.xxkqp.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");
        MongoDatabase database = createMongoDatabase(client, "sample_mflix");

//        MongoClient client = createMongoClient("mongodb://localhost:27017");
//        MongoDatabase database = createMongoDatabase(client, "sample");

        MongoCollection<Document> collection = createMongoCollection(database, "movies");

        insertOne(collection);
        insertMany(collection);

//        findToFileAsync(collection);
        find(collection);
        findFirst(collection);

        updateOne(collection);
        updateMany(collection);

        deleteOne(collection);
        deleteMany(collection);

        withTransaction(client, collection);

        aggregateMatchAndGroup(collection);
        aggregateSortAndProject(collection);
    }


    private static void aggregateSortAndProject(MongoCollection<Document> collection) {
        System.out.println("\n----- Aggregation with Match, Group, Sort and Project-----");
        // Match movies with IMDb rating greater than 7.0
        Bson match = Aggregates.match(Filters.gt("imdb.rating", 8.0));
        // Group by "rated" field and count the number of movies in each group
        Bson group = Aggregates.group(
                "$rated",
                new BsonField("count", new Document("$sum", 1))
        );
        // Sort the groups in descending order by the count
        Bson sort = Aggregates.sort(Sorts.ascending("count"));
        // Project only the "rated" field (renaming it to "rating") and "count"
        Bson project = Aggregates.project(
                new Document("rating", "$_id")
                        .append("count", 1)
                        .append("_id", 0)  // Exclude the original _id field
        );
        // Run the aggregation pipeline
        AggregateIterable<Document> iterable = collection.aggregate(
                Arrays.asList(match, group, sort, project)
        );
        // Collect the results into a list
        iterable.into(new java.util.ArrayList<>()).forEach(System.out::println);
    }


    public static void aggregateMatchAndGroup(MongoCollection<Document> collection) {
        System.out.println("\n----- Aggregation with Match and Group-----");
        // Match movies with IMDb rating greater than 7.0
        Bson match = Aggregates.match(Filters.gt("imdb.rating", 0));
        // Group by the "rated" field and count the number of movies for each rating
        Bson group = Aggregates.group("$rated",  // Group by the "rated" field
                new BsonField("count", new Document("$sum", 1))  // Sum up to get the count
        );
        // Run the aggregation pipeline
        AggregateIterable<Document> iterable = collection.aggregate(Arrays.asList(match, group));
        // Print the results
        iterable.forEach(document -> System.out.println("Rating: " + document.getString("_id") + ", Count: " + document.getInteger("count")));
        iterable.forEach(document -> System.out.println(document.toJson()));
    }


    private static void withTransaction(MongoClient client, MongoCollection<Document> collection) {
        System.out.println("\n----- With Transaction -----");
        // Insert a new document
        collection.insertOne(new Document()
                .append("title", "Deletable Movie 1")
                .append("year", 2025)
                .append("type", "temp"));

        System.out.println("Before Transaction -----");
        // Query and projection to find the inserted document
        Bson query = Filters.eq("title", "Deletable Movie 1");
        Bson projection = Projections.fields(
                Projections.include("title", "released", "year", "type"),
                Projections.excludeId());
        Document document = collection.
                find(query)
                .projection(projection)
                .first();
        System.out.println(document);

        ClientSession session = client.startSession();              // (1) Start a client session for the transaction

        TransactionBody<String> transaction = () -> {               // (2) Define the transaction logic using a TransactionBody
            Bson update = Updates.set("type", "film");              // (3) Update the document's type within the session
            UpdateResult updateResult = collection.updateOne(session, query, update);
            // Print the result of the update operation
            System.out.printf("UpdateOneResult: Matched = %d, Modified = %d, Acknowledged = %b\n",
                    updateResult.getMatchedCount(),
                    updateResult.getModifiedCount(),
                    updateResult.wasAcknowledged());

            // Simulate a failure to demonstrate transaction rollback
            if (false) throw new RuntimeException("Transaction doesn't work.");

            // Return success message
            return "Transaction completed successfully.";
        };

        try {
            String result = session.withTransaction(transaction);   // (4) Execute the transaction
            System.out.println(result);
        } catch (RuntimeException e) {
            // Handle transaction failure
            System.err.println("Transaction failed: " + e.getMessage());
        } finally {
            session.close();                                        // (5) Close the session
        }

        System.out.println("After Transaction -----");
        // Verify the document after the transaction (outside the session)
        Bson query1 = Filters.eq("title", "Deletable Movie 1");
        Bson projection1 = Projections.fields(
                Projections.include("title", "released", "year", "type"),
                Projections.excludeId());
        Document document1 = collection.
                find(query1)
                .projection(projection1)
                .first();
        System.out.println(document1);

        // Clean up: Delete the test document
        System.out.println(collection.deleteOne(query));
    }


    private static void deleteMany(MongoCollection<Document> collection) {
        System.out.println("\n----- Delete Many Documents -----");

        Bson query = Filters.regex("title", "^Sample Movie.*");
        DeleteResult deleted = collection.deleteMany(query);

        System.out.printf(deleted.wasAcknowledged() ? "Delete ManyResult: Deleted Count = %s\n" : "Document found", deleted.getDeletedCount());
    }

    private static void deleteOne(MongoCollection<Document> collection) {
        System.out.println("\n----- Delete One Document -----");
        Bson query = Filters.eq("_id", id);
        DeleteResult result = collection.deleteOne(query);
        System.out.printf(result.wasAcknowledged() ? "Document deleted status: %s\n" : " No document found.", result);
    }

    private static void updateMany(MongoCollection<Document> collection) {
        System.out.println("\n----- Update Many Documents -----");

        Bson query = Filters.regex("title", "^Sample.*");
        Bson updates = Updates.set("type", "film");

        UpdateResult result = collection.updateMany(query, updates);
        System.out.printf("UpdateManyResult: Matched = %d, Modified = %d, Acknowledged = %b\n", result.getMatchedCount(), result.getModifiedCount(), result.wasAcknowledged());
        collection.find(Filters.eq("type", "film")).forEach(System.out::println);
    }

    private static void updateOne(MongoCollection<Document> collection) {
        System.out.println("\n----- Update One Document -----");

//        Bson query = Filters.eq("_id", id);
        Bson projection = Projections.fields(Projections.include("title", "released", "year", "type"), Projections.excludeId());
//        Document document = collection.find(query).projection(projection).first();
//        System.out.println(document != null ? "Before Update:\n" + document : "No document found.");

//        query = Filters.eq("_id", id);
//        Bson updates = Updates.set("type", "updated");

        Bson query = Filters.eq("year", "2014è");
        Bson updates = Updates.set("year", "2014");

        UpdateResult upResult = collection.updateOne(query, updates);
        System.out.printf("UpdateOneResult: Matched = %d, Modified = %d, Acknowledged = %b\n", upResult.getMatchedCount(), upResult.getModifiedCount(), upResult.wasAcknowledged());
        Document document = collection.find(query).projection(projection).first();
        System.out.println(document != null ? "After Update:\n" + document : "No document found.");
    }


    private static void findFirst(MongoCollection<Document> collection) {
        System.out.println("\n----- Finding First Document with Filters -----");

        Bson filter = Filters.exists("year", true);

        Bson projection = Projections.fields(
                Projections.include("title", "released", "year", "type"),
                Projections.excludeId());

        Bson sort = Sorts.ascending("year");
        Bson tros = Sorts.descending("year");

        Document document = collection.find(filter).projection(projection).sort(sort).first();
        System.out.println(document != null ? "Document found:\n" + document : "No document found.");

        Document second = collection.find(filter).projection(projection).sort(tros).skip(1).first();
        System.out.println(document != null ? "Document found:\n" + second : "No document found.");
    }

    private static void find(MongoCollection<Document> collection) {
        System.out.println("\n----- Finding Documents with Filters -----");

        Bson query = Filters.regex("title", ".*" + "Sample" + ".*", "i");
        Bson projection = Projections.fields(
                Projections.include("title", "released", "year", "type"),
                Projections.excludeId());

//        Bson query = Filters.regex("title", "^Sample.*");
        FindIterable<Document> findIterable = collection.find(query).projection(projection);
        for (Document document : findIterable) {
            System.out.println(document.toString());
        }
    }

    private static void findToFileAsync(MongoCollection<Document> collection) {
        System.out.println("\n----- Finding Documents with Filters and Writing to File Asynchronously -----");
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = "data_" + timestamp + ".json";
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            executor.submit(() -> {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
                    for (Document document : collection.find()) {
                        writer.write(document.toJson());
                        writer.newLine();
                    }
                    System.out.println("Documents successfully written to output.txt");
                } catch (IOException e) {
                    System.err.println("Error writing to file: " + e.getMessage());
                }
            });
            executor.shutdown();
        } catch (Exception e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void insertMany(MongoCollection<Document> collection) {
        System.out.printf("\n----- Inserting Many Documents in Collection: \"%s\" -----\n", collection);
        Document movie1 = new Document().append("title", "Sample Movie 1").append("year", 2021);
        Document movie2 = new Document().append("title", "Sample Movie 2").append("year", 2022);
        Document movie3 = new Document().append("title", "Sample Movie 3").append("year", 2023);
        Document movie4 = new Document().append("title", "Sample Movie 4").append("year", 2024);
        Document movie5 = new Document().append("title", "Sample Movie 5").append("year", 2025);

        List<Document> documents = Arrays.asList(movie1, movie2, movie3, movie4, movie5);
        InsertManyResult insertManyResult = collection.insertMany(documents);
        System.out.println("InsertManyResult IDs:");
        insertManyResult.getInsertedIds().forEach((key, value) -> System.out.println((value)));
    }

    private static void insertOne(MongoCollection<Document> collection) {
        System.out.printf("\n----- Inserting One Document in Collection: \"%s\" -----\n", collection);
        Document awards = new Document()
                .append("wins", 3)
                .append("nominations", 10)
                .append("text", "Won 3 out of 10 awards");

        Document viewer = new Document()
                .append("rating", 85)
                .append("numReviews", 1000)
                .append("meter", 90);

        Document tomatoes = new Document()
                .append("viewer", viewer)
                .append("lastUpdated", LocalDate.of(2023, 10, 13).toString());

        Document imdb = new Document()
                .append("rating", 8)
                .append("votes", 5000);

        Document document = new Document()
                .append("plot", "A thrilling adventure.")
                .append("genres", List.of("Action", "Adventure"))
                .append("runtime", 120)
                .append("cast", List.of("John Doe", "Jane Smith"))
                .append("numMflixComments", 5)
                .append("poster", "poster_url")
                .append("title", "Sample Movie")
                .append("fullPlot", "A full plot of the sample document.")
                .append("languages", List.of("English", "Spanish"))
                .append("released", LocalDate.of(2023, 10, 10).toString())
                .append("directors", List.of("Director 1", "Director 2"))
                .append("writers", List.of("Writer 1", "Writer 2"))
                .append("awards", awards)
                .append("lastUpdated", "2023-10-13")
                .append("year", 2030)
                .append("imdb", imdb)
                .append("countries", List.of("USA", "Canada"))
                .append("type", "movie")
                .append("tomatoes", tomatoes).
                append("plotEmbedding", List.of(0.1, 0.2, 0.3));

        InsertOneResult result = collection.insertOne(document);

        id = result.getInsertedId();
        System.out.printf("Insert One ID: {insertedId: %s}\n", id);
    }

    private static MongoCollection<Document> createMongoCollection(MongoDatabase database, String collectionName) {
        System.out.printf("\n----- Connecting to MongoDB collection: \"%s\" -----\n", collectionName);
        MongoCollection<Document> mongoCollection = database.getCollection(collectionName);
        System.out.printf("Connected to MongoCollection: %s\n", mongoCollection);
        return mongoCollection;
    }

    private static MongoDatabase createMongoDatabase(MongoClient client, String databaseName) {
        System.out.printf("\n----- Connecting to MongoDB database: \"%s\" -----\n", databaseName);
        MongoDatabase mongoDatabase = client.getDatabase(databaseName);
        System.out.printf("Connected to database: %s\n", mongoDatabase);
        return mongoDatabase;
    }

    private static MongoClient createMongoClient(String connectionString) {
        System.out.printf("\n----- Connecting to MongoDB with connection string: \"%s\" -----\n", connectionString);
        MongoClient mongoClient = MongoClients.create(MongoClientSettings.builder().applyConnectionString(new ConnectionString(connectionString)).build());
        System.out.printf("Connected to MongoDB: %s\n", mongoClient);
        return mongoClient;
    }
}
