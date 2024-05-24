package com.nobblecrafts.challenge.devsecopssr.domain.core.entity;

import com.nobblecrafts.challenge.devsecopssr.dataaccess.useractivity.entity.UserActivityEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class MovieEvaluation {
    private Long id;
    private Long movieId;
    private MovieStatus status;
    private Long user;
}
