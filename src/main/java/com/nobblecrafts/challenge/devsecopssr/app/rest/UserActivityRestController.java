package com.nobblecrafts.challenge.devsecopssr.app.rest;

import com.nobblecrafts.challenge.devsecopssr.domain.UserActivityService;
import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.MovieStatus;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieDetailsWithEvaluation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "user/activity", produces = "application/vnd.api.v1+json")
public class UserActivityRestController {
    private final UserActivityService userActivityService;

    @PostMapping("favorite")
    public ResponseEntity<MovieDetailsWithEvaluation> markAsFavorite(@RequestBody @Valid MovieDetailsWithEvaluation evaluation) {
        log.info("\nMARK AS FAVORITE: {}\n", evaluation);
        MovieDetailsWithEvaluation output = userActivityService.evaluateMovie(MovieDetailsWithEvaluation.builder()
                .movieDetails(evaluation.movieDetails())
                .status(MovieStatus.FAVORITE)
                .build());
        return ResponseEntity.ok().body(output);
    }

    @PostMapping("want-to-watch")
    public ResponseEntity<MovieDetailsWithEvaluation> markAsWantToWatch(@RequestBody @Valid MovieDetailsWithEvaluation evaluation) {
        log.info("\nMARK AS WANT-TO-WATCH: {}\n", evaluation);
        MovieDetailsWithEvaluation output = userActivityService.evaluateMovie(MovieDetailsWithEvaluation.builder()
                .movieDetails(evaluation.movieDetails())
                .status(MovieStatus.WANT_WATCH)
                .build());
        return ResponseEntity.ok().body(output);
    }

    @PostMapping("watched")
    public ResponseEntity<MovieDetailsWithEvaluation> markAsWatched(@RequestBody @Valid MovieDetailsWithEvaluation evaluation) {
        log.info("\nMARK AS WATCHED: {}\n", evaluation);
        MovieDetailsWithEvaluation output = userActivityService.evaluateMovie(MovieDetailsWithEvaluation.builder()
                .movieDetails(evaluation.movieDetails())
                .status(MovieStatus.WATCHED)
                .build());
        return ResponseEntity.ok().body(output);
    }
}
