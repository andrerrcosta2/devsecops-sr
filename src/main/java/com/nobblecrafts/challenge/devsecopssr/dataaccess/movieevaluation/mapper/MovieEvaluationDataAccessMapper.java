package com.nobblecrafts.challenge.devsecopssr.dataaccess.movieevaluation.mapper;

import com.nobblecrafts.challenge.devsecopssr.dataaccess.movieevaluation.entity.MovieEvaluationEntity;
import com.nobblecrafts.challenge.devsecopssr.dataaccess.useractivity.entity.UserActivityEntity;
import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.MovieEvaluation;
import org.springframework.stereotype.Component;

@Component
public class MovieEvaluationDataAccessMapper {

    public MovieEvaluationEntity toMovieEvaluationEntity(MovieEvaluation evaluation, UserActivityEntity userActivity) {
        return MovieEvaluationEntity.builder()
                .movieId(evaluation.getMovieId())
                .user(userActivity)
                .status(evaluation.getStatus())
                .build();
    }

    public MovieEvaluation toMovieEvaluation(MovieEvaluationEntity e) {
        return MovieEvaluation.builder()
                .id(e.getId())
                .movieId(e.getMovieId())
                .status(e.getStatus())
                .user(e.getUser().getId())
                .build();
    }

    public void updateMovieEvaluationEntity(MovieEvaluation evaluation, MovieEvaluationEntity e) {
        e.setStatus(evaluation.getStatus());
    }
}
