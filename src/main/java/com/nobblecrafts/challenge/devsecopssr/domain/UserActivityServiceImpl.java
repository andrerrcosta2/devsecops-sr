package com.nobblecrafts.challenge.devsecopssr.domain;

import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.MovieEvaluation;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieDetailsWithEvaluation;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieEvaluationCommand;
import com.nobblecrafts.challenge.devsecopssr.domain.service.mapper.UserActivityDataMapper;
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
    private final UserActivityDataMapper userActivityDataMapper;
    private final DomainSecurityContext securityContext;

    @Override
    @Transactional
    public MovieDetailsWithEvaluation evaluateMovie(MovieDetailsWithEvaluation evaluation) {
        String currentUser = securityContext.requireCurrentAccountUsername();
        MovieEvaluationCommand command = userActivityDataMapper.toMovieEvaluationCommand(evaluation, currentUser);
        MovieEvaluation evaluated = evaluateMovieHelper.updateMovieEvaluation(command);
        return userActivityDataMapper.toMovieDetailsEvaluation(evaluated, evaluation.movieDetails());
    }

}
