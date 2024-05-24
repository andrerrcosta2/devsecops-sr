package com.nobblecrafts.challenge.devsecopssr.dataaccess.movieevaluation.repository;

import com.nobblecrafts.challenge.devsecopssr.dataaccess.movieevaluation.entity.MovieEvaluationEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieEvaluationJpaRepository extends JpaRepository<MovieEvaluationEntity, Long> {

    @Query("SELECT me FROM MovieEvaluationEntity me WHERE me.user.id = :userActivityId AND me.movieId = :movieId")
    Optional<MovieEvaluationEntity> findByUserActivityIdAndMovieId2(@Param("userActivityId") Long userActivityId, @Param("movieId") Long movieId);
    @Query("SELECT me FROM MovieEvaluationEntity me WHERE me.user.id = :userActivityId AND me.movieId = :movieId")
    Optional<MovieEvaluationEntity> findByUserActivityIdAndMovieId(@Param("userActivityId") Long userActivityId, @Param("movieId") Long movieId);
}
