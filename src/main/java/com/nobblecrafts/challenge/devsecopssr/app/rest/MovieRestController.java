package com.nobblecrafts.challenge.devsecopssr.app.rest;

import com.nobblecrafts.challenge.devsecopssr.domain.MovieService;
import com.nobblecrafts.challenge.devsecopssr.domain.UserActivityService;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieDetailsWithEvaluation;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.TMDBMovie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "movies", produces = "application/vnd.api.v1+json")
public class MovieRestController {

    public static final String PATH = "/movies";
    private final MovieService movieService;

    @GetMapping("{page}")
    public ResponseEntity<Page<TMDBMovie>> getMoviesList(@PathVariable(name = "page", required = false) Optional<Integer> page) {
        var output = movieService.listMovies(page.orElse(1));
        log.info("Returning movies list: {}", output);
        return ResponseEntity.ok().body(output);

    }

    @GetMapping("/details/{movieId}")
    public ResponseEntity<MovieDetailsWithEvaluation> getMovieDetailsEvaluated(@PathVariable("movieId") Long movieId) {
        MovieDetailsWithEvaluation movieDetails = movieService.getMovieDetails(movieId);
        return ResponseEntity.ok().body(movieDetails);
    }
}
