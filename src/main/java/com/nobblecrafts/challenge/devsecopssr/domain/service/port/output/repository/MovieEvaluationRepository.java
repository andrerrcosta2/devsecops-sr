package com.nobblecrafts.challenge.devsecopssr.domain.service.port.output.repository;

import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.MovieEvaluation;

import java.util.List;
import java.util.Optional;

public interface MovieEvaluationRepository {

    List<MovieEvaluation> findAll();

    MovieEvaluation save(MovieEvaluation evaluation);

    Optional<MovieEvaluation> findByUserActivityIdAndMovieId(Long userActivityId, Long movieId);
}
