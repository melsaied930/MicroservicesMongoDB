package com.example.microservicesmongodb.records;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "movies")
public record Movie(
        @Id String id,
        String plot,
        List<String> genres,
        Integer runtime,
        List<String> cast,
        Integer numMflixComments,
        String poster,
        String title,
        String fullPlot,
        List<String> languages,
        LocalDate released,
        List<String> directors,
        List<String> writers,
        Awards awards,
        String lastUpdated,
        String year,
        Imdb imdb,
        List<String> countries,
        String type,
        Tomatoes tomatoes,
        List<Double> plotEmbedding
) {
}

record Awards(
        Integer wins,
        Integer nominations,
        String text
) {
}

record Imdb(
        Integer rating,
        Integer votes,
        Integer id
) {
}

record Tomatoes(
        Viewer viewer,
        LocalDate lastUpdated
) {
}

record Viewer(
        Integer rating,
        Integer numReviews,
        Integer meter
) {
}
