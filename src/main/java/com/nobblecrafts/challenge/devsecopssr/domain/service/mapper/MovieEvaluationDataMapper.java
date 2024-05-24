package com.nobblecrafts.challenge.devsecopssr.domain.service.mapper;

import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.MovieEvaluation;
import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.MovieStatus;
import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.UserActivity;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieDetailsWithEvaluation;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieEvaluationCommand;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.TMDBMovieDetails;
import org.springframework.stereotype.Component;

@Component
public class MovieEvaluationDataMapper {
    public MovieEvaluation toMovieEvaluation(MovieEvaluationCommand command, UserActivity userActivity) {
        return MovieEvaluation.builder()
                .movieId(command.movieId())
                .status(command.evaluation())
                .user(userActivity.getId())
                .build();
    }

    public MovieDetailsWithEvaluation toMovieDetailsWithEvaluation(TMDBMovieDetails movieDetails, MovieStatus status) {
        return MovieDetailsWithEvaluation.builder()
                .movieDetails(movieDetails)
                .status(status)
                .build();
    }


}
