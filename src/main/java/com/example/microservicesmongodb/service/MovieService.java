package com.example.microservicesmongodb.service;

import com.example.microservicesmongodb.records.Movie;
import com.example.microservicesmongodb.repositories.MovieRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class MovieService {

    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public List<Movie> getAllMoviesRepository() {
        return repository.findAll();
    }

    public Movie getMovieById(String id) {
        return repository.findById(id).orElse(null);
    }

    public List<Movie> getMoviesByTitle(String title) {
        return repository.findByTitle(title);
    }

    public Movie saveMovie(Movie movie) {
        return repository.save(movie);
    }

    public void deleteMovie(String id) {
        repository.deleteById(id);
    }
}
