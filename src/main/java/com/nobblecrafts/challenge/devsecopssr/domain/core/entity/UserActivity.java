package com.nobblecrafts.challenge.devsecopssr.domain.core.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
@Getter
public class UserActivity {
    private Long id;
    private Set<Long> movieEvaluations;
    private Long account;
}
