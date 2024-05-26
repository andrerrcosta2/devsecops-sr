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

    public static final String PATH = "/user/activity";
    private final UserActivityService userActivityService;

    @PostMapping("favorite/{movieId}")
    public ResponseEntity<MovieDetailsWithEvaluation> markAsFavorite(@PathVariable("movieId") Long movieId) {
        log.info("\nMARK AS FAVORITE: {}\n", movieId);
        MovieDetailsWithEvaluation output = userActivityService.evaluateMovie(movieId, MovieStatus.FAVORITE);
        return ResponseEntity.ok().body(output);
    }

    @PostMapping("want-to-watch/{movieId}")
    public ResponseEntity<MovieDetailsWithEvaluation> markAsWantToWatch(@PathVariable("movieId") Long movieId) {
        log.info("\nMARK AS WANT-TO-WATCH: {}\n", movieId);
        MovieDetailsWithEvaluation output = userActivityService.evaluateMovie(movieId, MovieStatus.WANT_WATCH);
        return ResponseEntity.ok().body(output);
    }

    @PostMapping("watched/{movieId}")
    public ResponseEntity<MovieDetailsWithEvaluation> markAsWatched(@PathVariable("movieId") Long movieId) {
        log.info("\nMARK AS WATCHED: {}\n", movieId);
        MovieDetailsWithEvaluation output = userActivityService.evaluateMovie(movieId, MovieStatus.WATCHED);
        return ResponseEntity.ok().body(output);
    }
}
