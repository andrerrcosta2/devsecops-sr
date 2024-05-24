package com.nobblecrafts.challenge.devsecopssr.security.core.userdetails;

import com.nobblecrafts.challenge.devsecopssr.dataaccess.account.repository.AccountJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountDetailsService implements UserDetailsService {

    private final AccountJpaRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("\n\nAccountDetailsService: {}\n\n", username);
        return repository
                .findByUsername(username)
                .map(AccountDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User name not found: " + username));
    }
}