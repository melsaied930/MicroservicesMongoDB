package com.example.microservicesmongodb.controller;

import com.example.microservicesmongodb.records.Movie;
import com.example.microservicesmongodb.service.MovieService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping()
    public List<Movie> getAllMoviesRepository() {
        return service.getAllMoviesRepository();
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable String id) {
        return service.getMovieById(id);
    }

    @GetMapping("/search")
    public List<Movie> getMoviesByTitle(@RequestParam String title) {
        return service.getMoviesByTitle(title);
    }

    @PostMapping
    public Movie createMovie(@RequestBody Movie movie) {
        return service.saveMovie(movie);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable String id) {
        service.deleteMovie(id);
    }
}
