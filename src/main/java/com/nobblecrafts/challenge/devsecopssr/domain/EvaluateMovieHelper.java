package com.nobblecrafts.challenge.devsecopssr.domain;

import com.nobblecrafts.challenge.devsecopssr.dataaccess.useractivity.repository.UserActivityJpaRepository;
import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.MovieEvaluation;
import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.UserActivity;
import com.nobblecrafts.challenge.devsecopssr.domain.core.exception.DomainNotFoundException;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieDetailsWithEvaluation;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieEvaluationCommand;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.TMDBMovieDetails;
import com.nobblecrafts.challenge.devsecopssr.domain.service.mapper.MovieEvaluationDataMapper;
import com.nobblecrafts.challenge.devsecopssr.domain.service.port.output.client.TMDBClient;
import com.nobblecrafts.challenge.devsecopssr.domain.service.port.output.repository.MovieEvaluationRepository;
import com.nobblecrafts.challenge.devsecopssr.domain.service.port.output.repository.UserActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class EvaluateMovieHelper {

    private final MovieEvaluationDataMapper movieEvaluationDataMapper;
    private final UserActivityRepository userActivityRepository;
    private final MovieEvaluationRepository movieEvaluationRepository;
    private final TMDBClient tmdbClient;

    @Transactional
    public MovieDetailsWithEvaluation updateMovieEvaluation(MovieEvaluationCommand command) {
        UserActivity activity = getUser(command);
        MovieEvaluation evaluation = movieEvaluationDataMapper.toMovieEvaluation(command, activity);
        TMDBMovieDetails movieDetails = getMovieDetails(command);
        MovieEvaluation evaluated = persistEvaluation(evaluation);
        return movieEvaluationDataMapper.toMovieDetailsWithEvaluation(movieDetails, evaluated.getStatus());
    }

    private UserActivity getUser(MovieEvaluationCommand command) {
        Optional<UserActivity> optional = userActivityRepository.findByUsername(command.currentUser());
        if(optional.isEmpty()) {
            log.error("Security error, evaluating movie without account on database");
            throw new AccessDeniedException("Unauthorized Access");
        }
        return optional.get();
    }

    private TMDBMovieDetails getMovieDetails(MovieEvaluationCommand command) {
        TMDBMovieDetails movieDetails = tmdbClient.getMovieDetails(command.movieId());
        if(movieDetails == null) throw new DomainNotFoundException("The movie " + command.movieId() + " was not found");
        return movieDetails;
    }

    private MovieEvaluation persistEvaluation(MovieEvaluation evaluation) {
        return movieEvaluationRepository.save(evaluation);
    }

}
