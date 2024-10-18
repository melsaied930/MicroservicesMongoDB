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

@Service
public class MongoDB_CRUD_Operations {

    private final MongoCollection<Document> collection;
    private BsonValue id;

    public MongoDB_CRUD_Operations(MongoCollection<Document> moviesCollection) {
        this.collection = moviesCollection;
    }

    public void start() {
        insertOne();
        insertMany();

        find();
        findFirst();

        updateOne();
        updateMany();

        deleteOne();
        deleteMany();
    }

    private void deleteMany() {
        System.out.println("\n----- Delete Many Documents in Collection -----");


        Bson query = Filters.regex("title", "^Sample Movie.*");
        DeleteResult deleteResult = collection.deleteMany(query);

        System.out.printf(deleteResult.wasAcknowledged() ? "Documents deleted status: Deleted Count = %s\n" : "Document found",
                deleteResult.getDeletedCount());
    }

    private void deleteOne() {
        System.out.println("\n----- Delete One Documents in Collection -----");

        Bson query = Filters.eq("_id", id);
        DeleteResult deleteResult = collection.deleteOne(query);
        System.out.printf(deleteResult.wasAcknowledged() ? "Document deleted status: %s\n" : " No document found.", deleteResult);
    }

    private void updateMany() {
        System.out.println("\n----- Update Many Documents in Collection -----");

        Bson query = Filters.regex("title", "^Sample.*");
        Bson projection = Projections.fields(
                Projections.include("title", "year", "type"),
                Projections.excludeId());

        System.out.println("Before Update:");
        FindIterable<Document> findIterable = collection.find(query).projection(projection);
        findIterable.forEach(System.out::println);

        Bson updates = Updates.set("type", "film");
        UpdateResult updateResult = collection.updateMany(query, updates);

        System.out.println("After Update:");
        findIterable = collection.find(query).projection(projection);
        findIterable.forEach(System.out::println);

        System.out.printf("\nUpdateManyResult: Matched = %d, Modified = %d, Acknowledged = %b\n",
                updateResult.getMatchedCount(),
                updateResult.getModifiedCount(),
                updateResult.wasAcknowledged());
    }

    private void updateOne() {
        System.out.println("\n----- Update One Documents in Collection -----");

        Bson query = Filters.eq("year", "2014è");
        Bson updates = Updates.set("year", "2014");
        collection.updateOne(query, updates);

        query = Filters.eq("_id", id);

        Bson projection = Projections.fields(
                Projections.include("title", "released", "year", "type"),
                Projections.excludeId());

        Document document = collection.find(query).projection(projection).first();
        System.out.println(document != null ? "Before Update:\n" + document : "No document found.");

        updates = Updates.set("type", "film");
        UpdateResult updateOne = collection.updateOne(query, updates);

        document = collection.find(query).projection(projection).first();
        System.out.println(document != null ? "After Update:\n" + document : "No document found.");

        System.out.printf("\nUpdateOneResult: Matched = %d, Modified = %d, Acknowledged = %b\n", updateOne.getMatchedCount(), updateOne.getModifiedCount(), updateOne.wasAcknowledged());
    }

    private void findFirst() {
        System.out.println("\n----- Find First Documents in Collection -----");

        Bson filter = Filters.exists("year", true);

        Bson projection = Projections.fields(
                Projections.include("title", "released", "year", "type"),
                Projections.excludeId());

        Bson sort = Sorts.ascending("year");
        Bson tros = Sorts.descending("year");

        Document document = collection.find(filter).projection(projection).sort(sort).first();
        System.out.println(document != null ? document : "No document found.");

        Document second = collection.find(filter).projection(projection).sort(tros).skip(1).first();
        System.out.println(document != null ? second : "No document found.");
    }

    private void find() {
        System.out.println("\n----- Find Many Documents in Collection -----");

        //Bson query = Filters.regex("title", ".*Sample.*", "i");
        Bson query = Filters.regex("title", "^Sample Movie.*");
        FindIterable<Document> findIterable = collection.find(query);
        for (Document document : findIterable) {
            System.out.println(document != null ? document : "No document found.");
        }
    }

    private void insertMany() {
        System.out.println("\n----- Inserting Many Documents in Collection -----");

        Document movie1 = new Document().append("title", "Sample Movie 1").append("year", 2021);
        Document movie2 = new Document().append("title", "Sample Movie 2").append("year", 2022);
        Document movie3 = new Document().append("title", "Sample Movie 3").append("year", 2023);
        Document movie4 = new Document().append("title", "Sample Movie 4").append("year", 2024);
        Document movie5 = new Document().append("title", "Sample Movie 5").append("year", 2025);

        List<Document> documents = Arrays.asList(movie1, movie2, movie3, movie4, movie5);

        InsertManyResult insertManyResult = collection.insertMany(documents);

        insertManyResult.getInsertedIds().forEach((key, value) -> System.out.printf("Inserted ID: %s\n", value));
    }

    private void insertOne() {
        System.out.println("\n----- Inserting One Document in Collection -----");

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
        System.out.printf("Inserted ID: %s\n", id);
    }
}
