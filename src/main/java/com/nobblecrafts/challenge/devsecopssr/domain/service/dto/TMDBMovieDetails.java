package com.nobblecrafts.challenge.devsecopssr.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Builder
public record TMDBMovieDetails(
        @JsonProperty("id")
        Long id,

        @JsonProperty("adult")
        boolean adult,

        @JsonProperty("backdrop_path")
        String backdropPath,

        @JsonProperty("belongs_to_collection")
        TMDBMovieCollection belongsToCollection,

        @JsonProperty("budget")
        Long budget,

        @JsonProperty("genres")
        List<TMDBGenre> genres,

        @JsonProperty("homepage")
        String homepage,

        @JsonProperty("imdb_id")
        String imdbId,

        @JsonProperty("origin_country")
        List<String> originalCountry,

        @JsonProperty("original_language")
        String originalLanguage,

        @JsonProperty("original_title")
        String originalTitle,

        @JsonProperty("overview")
        String overview,

        @JsonProperty("popularity")
        float popularity,

        @JsonProperty("poster_path")
        String posterPath,

        @JsonProperty("production_companies")
        List<TMDBProductionCompany> productionCompanies,

        @JsonProperty("production_countries")
        List<TMDBProductionCountry> productionCountries,

        @JsonProperty("release_date")
        Date releaseDate,

        @JsonProperty("revenue")
        Long revenue,

        @JsonProperty("runtime")
        Long runtime,

        @JsonProperty("spoken_languages")
        List<TMDBSpokenLanguage> spokenLanguages,

        @JsonProperty("status")
        String status,

        @JsonProperty("tagline")
        String tagline,

        @JsonProperty("title")
        String title,

        @JsonProperty("video")
        boolean video,

        @JsonProperty("vote_average")
        float voteAverage,

        @JsonProperty("vote_count")
        Long voteCount
) {}
