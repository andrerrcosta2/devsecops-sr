package com.nobblecrafts.challenge.devsecopssr.domain;

import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.MovieEvaluation;
import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.MovieStatus;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieDetailsWithEvaluation;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.TMDBMovie;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.TMDBMovieDetails;
import com.nobblecrafts.challenge.devsecopssr.domain.service.mapper.MovieEvaluationDataMapper;
import com.nobblecrafts.challenge.devsecopssr.domain.service.port.output.repository.MovieEvaluationRepository;
import com.nobblecrafts.challenge.devsecopssr.security.context.DomainSecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final CallTMDBHelper movieIntegrationHelper;
    private final MovieEvaluationDataMapper movieEvaluationDataMapper;
    private final MovieEvaluationRepository movieEvaluationRepository;
    private final DomainSecurityContext securityContext;

    @Override
    @Transactional(readOnly = true)
    public Page<TMDBMovie> listMovies(int page) {
        return movieIntegrationHelper.listMovies(page);
    }

    @Override
    @Transactional(readOnly = true)
    public MovieDetailsWithEvaluation getMovieDetails(Long movieId) {
        TMDBMovieDetails movieDetails = movieIntegrationHelper.getMovieDetails(movieId);
        Long accountId = securityContext.requireCurrentSecurityAccountDTO().id();

        Optional<MovieEvaluation> movieEvaluation = movieEvaluationRepository
                .findByUserActivityIdAndMovieId(accountId, movieId);

        if (movieEvaluation.isPresent()) {
            return movieEvaluationDataMapper.toMovieDetailsWithEvaluation(movieDetails, movieEvaluation.get().getStatus());
        } else {
            return movieEvaluationDataMapper.toMovieDetailsWithEvaluation(movieDetails, MovieStatus.NONE);
        }
    }
}
