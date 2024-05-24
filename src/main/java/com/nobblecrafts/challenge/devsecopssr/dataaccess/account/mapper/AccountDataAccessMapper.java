package com.nobblecrafts.challenge.devsecopssr.dataaccess.account.mapper;

import com.nobblecrafts.challenge.devsecopssr.dataaccess.account.entity.AccountEntity;
import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountDataAccessMapper {

    public AccountEntity toAccountEntity(Account account) {
        return AccountEntity.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .build();
    }

    public Account toAccount(AccountEntity e) {
        return Account.builder()
                .id(e.getId())
                .username(e.getUsername())
                .password("[hidden]")
                .build();
    }
}
