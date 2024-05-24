package com.nobblecrafts.challenge.devsecopssr.init;

import com.nobblecrafts.challenge.devsecopssr.domain.AccountService;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.RegisterAccountRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("dev")
public class DataInitializer implements CommandLineRunner {

    private final AccountService accountService;

    public DataInitializer(AccountService accountService, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        createDefaultAccount();
    }

    private void createDefaultAccount() {
        RegisterAccountRequest request = new RegisterAccountRequest("andrerrcosta", "Brasil2025$");
        accountService.register(request);
        System.out.println("Default account created: " + request.username());
    }
}
