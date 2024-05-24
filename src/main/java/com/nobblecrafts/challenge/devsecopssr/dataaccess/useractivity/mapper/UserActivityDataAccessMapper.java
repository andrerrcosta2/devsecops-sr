package com.nobblecrafts.challenge.devsecopssr.dataaccess.useractivity.mapper;

import com.nobblecrafts.challenge.devsecopssr.dataaccess.account.entity.AccountEntity;
import com.nobblecrafts.challenge.devsecopssr.dataaccess.movieevaluation.entity.MovieEvaluationEntity;
import com.nobblecrafts.challenge.devsecopssr.dataaccess.useractivity.entity.UserActivityEntity;
import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.UserActivity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserActivityDataAccessMapper {

    public UserActivityEntity toUserActivityEntity(AccountEntity acc) {
        return UserActivityEntity.builder()
                .account(acc)
                .build();
    }

    public UserActivity toUserActivity(UserActivityEntity e) {
        return UserActivity.builder()
                .id(e.getId())
                .movieEvaluations(e.getMovieEvaluations().stream()
                        .map(MovieEvaluationEntity::getId)
                        .collect(Collectors.toSet()))
                .account(e.getId())
                .build();
    }
}
