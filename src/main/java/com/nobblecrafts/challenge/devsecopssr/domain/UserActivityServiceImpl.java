package com.nobblecrafts.challenge.devsecopssr.domain;

import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.MovieEvaluation;
import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.MovieStatus;
import com.nobblecrafts.challenge.devsecopssr.domain.core.exception.DomainNotFoundException;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieDetailsWithEvaluation;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieEvaluationCommand;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.TMDBMovieDetails;
import com.nobblecrafts.challenge.devsecopssr.domain.service.mapper.UserActivityDataMapper;
import com.nobblecrafts.challenge.devsecopssr.domain.service.port.output.client.TMDBClient;
import com.nobblecrafts.challenge.devsecopssr.security.context.DomainSecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class UserActivityServiceImpl implements UserActivityService {

    private final EvaluateMovieHelper evaluateMovieHelper;
    private final DomainSecurityContext securityContext;


    @Override
    @Transactional
    public MovieDetailsWithEvaluation evaluateMovie(Long movieId, MovieStatus status) {
        String currentUser = securityContext.requireCurrentAccountUsername();
        return evaluateMovieHelper
                .updateMovieEvaluation(new MovieEvaluationCommand(movieId, currentUser, status));
    }
}
