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
}
