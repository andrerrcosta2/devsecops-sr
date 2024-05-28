package com.nobblecrafts.challenge.devsecopssr.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

// Essas classes também deveriam possuir constraints personalizadas
// de acodo com a resposta, pois são inputs
@Builder
public record TMDBMovie(
        int id,
        boolean adult,
        @JsonProperty("backdrop_path") String backdropPath,
        @JsonProperty("genre_ids") List<Long> genreIds,
        @JsonProperty("original_language") String originalLanguage,
        @JsonProperty("original_title") String originalTitle,
        String overview,
        float popularity,
        @JsonProperty("poster_path") String posterPath,
        @JsonProperty("release_date") Date releaseDate,
        String title,
        boolean video,
        @JsonProperty("vote_average") float voteAverage,
        @JsonProperty("vote_count") long voteCount
) {}