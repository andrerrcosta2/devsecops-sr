package com.nobblecrafts.challenge.devsecopssr.domain;

import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.Account;
import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.UserActivity;
import com.nobblecrafts.challenge.devsecopssr.domain.service.port.output.repository.UserActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class CreateUserActivityHelper {

    private final UserActivityRepository userActivityRepository;

    @Transactional
    public UserActivity initializeAndPersist(Account account) {
        UserActivity userActivity = createNewUserActivity(account);
        return userActivityRepository.save(userActivity);
    }

    private UserActivity createNewUserActivity(Account account) {
        return UserActivity.builder()
                .id(account.getId())
                .account(account.getId())
                .movieEvaluations(new HashSet<>())
                .build();
    }
}
