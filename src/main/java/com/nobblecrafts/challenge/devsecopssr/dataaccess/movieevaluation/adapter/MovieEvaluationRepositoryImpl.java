package com.nobblecrafts.challenge.devsecopssr.dataaccess.movieevaluation.adapter;

import com.nobblecrafts.challenge.devsecopssr.dataaccess.movieevaluation.entity.MovieEvaluationEntity;
import com.nobblecrafts.challenge.devsecopssr.dataaccess.movieevaluation.mapper.MovieEvaluationDataAccessMapper;
import com.nobblecrafts.challenge.devsecopssr.dataaccess.movieevaluation.repository.MovieEvaluationJpaRepository;
import com.nobblecrafts.challenge.devsecopssr.dataaccess.useractivity.entity.UserActivityEntity;
import com.nobblecrafts.challenge.devsecopssr.dataaccess.useractivity.repository.UserActivityJpaRepository;
import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.MovieEvaluation;
import com.nobblecrafts.challenge.devsecopssr.domain.core.exception.DomainNotFoundException;
import com.nobblecrafts.challenge.devsecopssr.domain.service.port.output.repository.MovieEvaluationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovieEvaluationRepositoryImpl implements MovieEvaluationRepository {

    private final MovieEvaluationJpaRepository movieEvaluationJpaRepository;
    private final UserActivityJpaRepository userActivityJpaRepository;
    private final MovieEvaluationDataAccessMapper mapper;

    @Override
    public List<MovieEvaluation> findAll() {
        return movieEvaluationJpaRepository.findAll().stream()
                .map(mapper::toMovieEvaluation)
                .collect(Collectors.toList());
    }

    @Override
    public MovieEvaluation save(MovieEvaluation evaluation) {
        Optional<MovieEvaluationEntity> optional = movieEvaluationJpaRepository
                .findByUserActivityIdAndMovieId(evaluation.getUser(), evaluation.getMovieId());

        if(optional.isPresent()) {
            var toUpdate = optional.get();
            mapper.updateMovieEvaluationEntity(evaluation, toUpdate);
            MovieEvaluationEntity saved = movieEvaluationJpaRepository.save(toUpdate);
            return mapper.toMovieEvaluation(saved);
        } else {
            UserActivityEntity uae = findUserActivityById(evaluation.getUser());
            MovieEvaluationEntity mee = movieEvaluationJpaRepository
                    .save(mapper.toMovieEvaluationEntity(evaluation, uae));
            return mapper.toMovieEvaluation(mee);
        }
    }

    @Override
    public Optional<MovieEvaluation> findByUserActivityIdAndMovieId(Long userActivityId, Long movieId) {
        return movieEvaluationJpaRepository.findByUserActivityIdAndMovieId(userActivityId, movieId)
                .map(mapper::toMovieEvaluation);
    }

    private UserActivityEntity findUserActivityById(Long id) {
        Optional<UserActivityEntity> e = userActivityJpaRepository.findById(id);
        if(e.isEmpty()) {
            log.error("The user associated with the movie evaluation does not exist on database: {}", id);
            throw new DomainNotFoundException("User Activity not found");
        }
        return e.get();
    }
}
