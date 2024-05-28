package com.nobblecrafts.challenge.devsecopssr.domain;

import com.nobblecrafts.challenge.devsecopssr.security.config.DomainSecurityConfigProperties;
import com.nobblecrafts.challenge.devsecopssr.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthCookieHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final DomainSecurityConfigProperties properties;

    public void buildJwtAuthenticationCookie(Authentication authentication, HttpServletResponse response) {
        String token = jwtTokenProvider.generateToken(authentication);
        setCookieOnResponse(token, response);
    }

    private void setCookieOnResponse(String token, HttpServletResponse response) {
        Cookie cookie = new Cookie(properties.getCookieName(), token);
        cookie.setPath("/");
        cookie.setMaxAge(properties.getCookieMaxAge());
        cookie.setHttpOnly(true);
        cookie.setDomain(properties.getCookieDomain());
        cookie.setSecure(properties.isRestrictCookieToHttps());
        response.addCookie(cookie);
    }
}
