package com.nobblecrafts.challenge.devsecopssr.domain;

import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieDetailsWithEvaluation;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.TMDBMovie;
import org.springframework.data.domain.Page;

public interface MovieService {
    Page<TMDBMovie> listMovies(int page);
    MovieDetailsWithEvaluation getMovieDetails(Long movieId);
}
