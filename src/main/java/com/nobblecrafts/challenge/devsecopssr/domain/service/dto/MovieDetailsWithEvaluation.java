package com.nobblecrafts.challenge.devsecopssr.domain.service.dto;

import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.MovieStatus;
import lombok.Builder;

@Builder
public record MovieDetailsWithEvaluation(
        MovieStatus status,
        TMDBMovieDetails movieDetails
) {
}
