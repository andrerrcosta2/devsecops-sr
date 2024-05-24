package com.nobblecrafts.challenge.devsecopssr.domain.service.mapper;

import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.MovieEvaluation;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieDetailsWithEvaluation;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieEvaluationCommand;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.TMDBMovieDetails;
import org.springframework.stereotype.Component;

@Component
public class UserActivityDataMapper {

    public MovieEvaluationCommand toMovieEvaluationCommand(MovieDetailsWithEvaluation e, String username) {
        return MovieEvaluationCommand.builder()
                .evaluation(e.status())
                .movieId(e.movieDetails().id())
                .currentUser(username)
                .build();
    }

    public MovieDetailsWithEvaluation toMovieDetailsEvaluation(MovieEvaluation me, TMDBMovieDetails md) {
        return MovieDetailsWithEvaluation.builder()
                .status(me.getStatus())
                .movieDetails(md)
                .build();
    }
}
