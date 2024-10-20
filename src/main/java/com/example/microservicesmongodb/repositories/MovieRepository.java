package com.example.microservicesmongodb.repositories;

import com.example.microservicesmongodb.records.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {
    List<Movie> findByTitle(String title);

    List<Movie> findByGenresContaining(String genre);

    List<Movie> findByYear(String year);

    // Custom MongoDB query to find movies by director name
    @Query("{ 'directors': ?0 }")
    List<Movie> findByDirector(String director);

    // Find movies released after a certain year
    @Query("{ 'year': { $gt: ?0 } }")
    List<Movie> findByYearGreaterThan(Integer year);
}
