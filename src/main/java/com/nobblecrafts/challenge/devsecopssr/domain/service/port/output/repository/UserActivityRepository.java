package com.nobblecrafts.challenge.devsecopssr.domain.service.port.output.repository;

import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.UserActivity;

import java.util.Optional;

public interface UserActivityRepository {
    UserActivity save(UserActivity activity);
    Optional<UserActivity> findByUserId(Long userId);

    Optional<UserActivity> findByUsername(String username);
}
