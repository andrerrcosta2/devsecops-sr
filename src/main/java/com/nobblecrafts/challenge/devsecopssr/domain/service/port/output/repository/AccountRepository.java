package com.nobblecrafts.challenge.devsecopssr.domain.service.port.output.repository;

import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.Account;

import java.util.Optional;

public interface AccountRepository {
    Account save(Account account);
    Optional<Account> findByUsername(String username);
    boolean existsByUsername(String username);
}
