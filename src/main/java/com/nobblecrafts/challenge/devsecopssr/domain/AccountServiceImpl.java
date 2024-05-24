package com.nobblecrafts.challenge.devsecopssr.domain;

import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.Account;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.RegisterAccountRequest;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.RegisterAccountResponse;
import com.nobblecrafts.challenge.devsecopssr.domain.service.mapper.AccountDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final RegisterAccountHelper registerAccountHelper;
    private final CreateUserActivityHelper createUserActivityHelper;

    @Override
    @Transactional
    public RegisterAccountResponse register(RegisterAccountRequest request) {
        Account account = registerAccountHelper.persistAccount(request);
        createUserActivityHelper.initializeAndPersist(account);
        return RegisterAccountResponse.builder()
                .message("Success")
                .build();
    }
}
