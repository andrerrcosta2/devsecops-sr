package com.nobblecrafts.challenge.devsecopssr.dataaccess.useractivity.adapter;

import com.nobblecrafts.challenge.devsecopssr.dataaccess.account.entity.AccountEntity;
import com.nobblecrafts.challenge.devsecopssr.dataaccess.account.repository.AccountJpaRepository;
import com.nobblecrafts.challenge.devsecopssr.dataaccess.useractivity.entity.UserActivityEntity;
import com.nobblecrafts.challenge.devsecopssr.dataaccess.useractivity.mapper.UserActivityDataAccessMapper;
import com.nobblecrafts.challenge.devsecopssr.dataaccess.useractivity.repository.UserActivityJpaRepository;
import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.UserActivity;
import com.nobblecrafts.challenge.devsecopssr.domain.core.exception.DomainNotFoundException;
import com.nobblecrafts.challenge.devsecopssr.domain.service.port.output.repository.UserActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class UserActivityRepositoryImpl implements UserActivityRepository {
    private final AccountJpaRepository accountJpaRepository;
    private final UserActivityJpaRepository userActivityJpaRepository;
    private final UserActivityDataAccessMapper mapper;

    @Override
    public UserActivity save(UserActivity activity) {
        AccountEntity ae = findAccountById(activity.getAccount());
        UserActivityEntity uae = userActivityJpaRepository.save(mapper.toUserActivityEntity(ae));
        return mapper.toUserActivity(uae);
    }

    @Override
    public Optional<UserActivity> findByUserId(Long accountId) {
        return userActivityJpaRepository.findById(accountId)
                .map(mapper::toUserActivity);
    }

    @Override
    public Optional<UserActivity> findByUsername(String username) {
        AccountEntity ae = findAccountByUsername(username);
        return findByUserId(ae.getId());
    }

    private AccountEntity findAccountById(Long id) {
        Optional<AccountEntity> ae = accountJpaRepository.findById(id);
        if(ae.isEmpty()) {
            log.error("Error, User activity isn't associated with any found account: {}", id);
            throw new DomainNotFoundException("User Activity must have an account associated");
        }
        return ae.get();
    }

    private AccountEntity findAccountByUsername(String username) {
        Optional<AccountEntity> ae = accountJpaRepository.findByUsername(username);
        if(ae.isEmpty()) {
            log.error("Error, User activity isn't associated with the username: {}", username);
            throw new DomainNotFoundException("User Activity must have an account associated");
        }
        return ae.get();
    }
}
