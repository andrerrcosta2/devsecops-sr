package com.nobblecrafts.challenge.devsecopssr.domain;

import com.nobblecrafts.challenge.devsecopssr.domain.core.exception.DomainException;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.LoginRequest;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.LoginResponse;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.RegisterAccountRequest;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.RegisterAccountResponse;
import com.nobblecrafts.challenge.devsecopssr.security.exception.AuthenticationException;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationHelper authenticationHelper;
    private final AuthCookieHandler authCookieHandler;

    @Override
    public LoginResponse login(LoginRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication authentication = authenticationHelper.authenticate(request);
        authCookieHandler.buildJwtAuthenticationCookie(authentication, response);
        return LoginResponse.builder().message("Success").build();
    }

}
