package com.example.microservicesmongodb;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MongoDB_Aggregation_Operations {

    private final MongoCollection<Document> collection;

    public MongoDB_Aggregation_Operations(MongoCollection<Document> moviesCollection) {
        this.collection = moviesCollection;
    }

    public void start() {

        aggregateMatchAndGroup();

        aggregateSortAndProject();

        aggregateMoviesInYear();
    }

    private void aggregateMoviesInYear() {
        System.out.println("\n----- MongoDB Aggregation Movies In Year -----");


        Bson query = Filters.exists("year");

        Bson matched = Aggregates.match(query);
        Document document = new Document("$sum", 1);
        BsonField bsonField = new BsonField("count", document);

        Bson sort = Sorts.ascending("count");

        Bson grouped = Aggregates.group("$year", bsonField);

        collection.aggregate(Arrays.asList(matched,grouped)).forEach(System.out::println);
    }

    private void aggregateSortAndProject() {
        System.out.println("\n----- MongoDB Aggregation Match, Group, Sort, and Project -----");

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

    private void aggregateMatchAndGroup() {
        System.out.println("\n----- MongoDB Aggregation Match and Group -----");

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

}
