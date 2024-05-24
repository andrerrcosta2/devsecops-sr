package com.nobblecrafts.challenge.devsecopssr.domain;

import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.MovieEvaluation;
import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.UserActivity;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieEvaluationCommand;
import com.nobblecrafts.challenge.devsecopssr.domain.service.mapper.MovieEvaluationDataMapper;
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

    @Transactional
    public MovieEvaluation updateMovieEvaluation(MovieEvaluationCommand command) {
        UserActivity activity = getUser(command);
        MovieEvaluation evaluation = movieEvaluationDataMapper.toMovieEvaluation(command, activity);
        return movieEvaluationRepository.save(evaluation);
    }

    private UserActivity getUser(MovieEvaluationCommand command) {
        Optional<UserActivity> optional = userActivityRepository.findByUsername(command.currentUser());
        if(optional.isEmpty()) {
            log.error("Security error, etc");
            throw new AccessDeniedException("Unauthorized Access");
        }
        return optional.get();
    }

}
