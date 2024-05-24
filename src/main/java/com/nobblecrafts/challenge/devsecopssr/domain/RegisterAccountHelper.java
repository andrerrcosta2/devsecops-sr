package com.nobblecrafts.challenge.devsecopssr.domain;

import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.Account;
import com.nobblecrafts.challenge.devsecopssr.domain.core.exception.DomainException;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.RegisterAccountRequest;
import com.nobblecrafts.challenge.devsecopssr.domain.service.mapper.AccountDataMapper;
import com.nobblecrafts.challenge.devsecopssr.domain.service.port.output.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RegisterAccountHelper {

    private final AccountRepository accountRepository;
    private final AccountDataMapper accountDataMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Account persistAccount(RegisterAccountRequest request) {
        if(usernameIsAvailable(request)) {
            Account account = accountDataMapper.toAccount(request, passwordEncoder.encode(request.password()));
            return accountRepository.save(account);
        }
        throw new DomainException("The username already exists");
    }

    private boolean usernameIsAvailable(RegisterAccountRequest request) {
        return !accountRepository.existsByUsername(request.username());
    }
}
