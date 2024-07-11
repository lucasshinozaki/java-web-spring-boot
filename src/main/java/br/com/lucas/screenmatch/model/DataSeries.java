package br.com.lucas.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DataSeries(
        @JsonAlias("Title") String title,
        @JsonAlias("totalSeasons") Integer totalSeasons,
        @JsonAlias("imdbRating") String filmReview) {
}