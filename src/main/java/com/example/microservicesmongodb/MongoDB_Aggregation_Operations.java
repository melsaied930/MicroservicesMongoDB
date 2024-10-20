package com.example.microservicesmongodb;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
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
        System.out.println("\n----- MongoDB Aggregation: Movies Grouped by Year, Projected, and Sorted -----");

        // Match Stage: Ensure 'year' field exists
        Bson matched = Aggregates.match(Filters.exists("year"));

        // Group Stage: Group by 'year' field and count occurrences
        BsonField countField = new BsonField("count", new Document("$sum", 1));
        Bson grouped = Aggregates.group("$year", countField);

        // Sort Stage: Sort by year in ascending order
        Bson sorted = Aggregates.sort(new Document("_id", 1)); // _id holds the year after grouping

        // Projection Stage: Reshape the output fields
        Bson projection = Aggregates.project(
                new Document("_id", 0) // Exclude the _id field (optional)
                        .append("year", "$_id") // Rename _id to 'year'
                        .append("count", 1) // Include the 'count' field
        );

        // Execute Aggregation Pipeline
        collection.aggregate(Arrays.asList(matched, grouped, sorted, projection)).forEach(System.out::println);
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
