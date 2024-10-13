package com.example.microservicesmongodb.controller;

import com.example.microservicesmongodb.records.Movie;
import com.example.microservicesmongodb.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping()
    public List<Movie> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Movie findById(@PathVariable String id) {
        return service.findById(id);
    }

    @GetMapping("/search")
    public List<Movie> findByTitle(@RequestParam String title) {
        return service.findByTitle(title);
    }

    @PostMapping
    public Movie save(@RequestBody Movie movie) {
        return service.save(movie);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        service.deleteById(id);
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<Movie>> getMoviesByYear(@PathVariable int year) {
        List<Movie> movies = service.getMoviesByYear(year);
        if (movies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/year-range")
    public ResponseEntity<List<Movie>> getMoviesByYearRange(
            @RequestParam int startYear,
            @RequestParam int endYear) {
        List<Movie> movies = service.getMoviesByYearRange(startYear, endYear);
        if (movies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(movies);
    }
}
