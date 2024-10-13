package com.example.microservicesmongodb.service;


import com.example.microservicesmongodb.records.Movie;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MongoTemplate template;

    public MovieService(MongoTemplate template) {
        this.template = template;
    }

    public List<Movie> findAll() {
        return template.findAll(Movie.class);
    }

    public Movie findById(String id) {
        Query query = new Query(Criteria.where("_id").is(new ObjectId(id)));
        return template.findOne(query, Movie.class);
    }

    public List<Movie> findByTitle(String title) {
        Query query = new Query(Criteria.where("title").is(title));
        return template.find(query, Movie.class);
    }

    public Movie save(Movie movie) {
        return template.save(movie);
    }

    public void deleteById(String id) {
        Query query = new Query(Criteria.where("_id").is(new ObjectId(id)));
        template.remove(query, Movie.class);
    }

    // Retrieve movies by year
    public List<Movie> getMoviesByYear(int year) {
        Query query = new Query(Criteria.where("year").is(year));
        return template.find(query, Movie.class);
    }

    // Example: Retrieve movies by a range of years
    public List<Movie> getMoviesByYearRange(int startYear, int endYear) {
        Query query = new Query(Criteria.where("year").gte(startYear).lte(endYear));
        return template.find(query, Movie.class);
    }
}
