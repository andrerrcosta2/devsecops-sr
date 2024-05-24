package com.nobblecrafts.challenge.devsecopssr.dataaccess.account.adapter;

import com.nobblecrafts.challenge.devsecopssr.dataaccess.account.entity.AccountEntity;
import com.nobblecrafts.challenge.devsecopssr.dataaccess.account.mapper.AccountDataAccessMapper;
import com.nobblecrafts.challenge.devsecopssr.dataaccess.account.repository.AccountJpaRepository;
import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.Account;
import com.nobblecrafts.challenge.devsecopssr.domain.service.port.output.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountDataAccessMapper mapper;
    private final AccountJpaRepository repository;
    @Override
    public Account save(Account account) {
        AccountEntity e = repository.save(mapper.toAccountEntity(account));
        return mapper.toAccount(e);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return repository.findByUsername(username)
                .map(mapper::toAccount);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }
}
