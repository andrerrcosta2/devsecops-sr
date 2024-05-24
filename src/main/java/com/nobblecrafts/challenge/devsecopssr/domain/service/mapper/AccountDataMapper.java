package com.nobblecrafts.challenge.devsecopssr.domain.service.mapper;

import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.Account;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.RegisterAccountRequest;
import org.springframework.stereotype.Component;

@Component
public class AccountDataMapper {
    public Account toAccount(RegisterAccountRequest request, String encodedPassword) {
        return Account.builder()
                .username(request.username())
                .password(encodedPassword)
                .build();
    }

}
