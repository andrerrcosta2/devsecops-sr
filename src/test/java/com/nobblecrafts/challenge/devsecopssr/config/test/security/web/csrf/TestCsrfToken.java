package com.nobblecrafts.challenge.devsecopssr.config.test.security.web.csrf;

import lombok.ToString;
import org.springframework.security.web.csrf.CsrfToken;

@ToString
public class TestCsrfToken implements CsrfToken {

    private String token;

    public TestCsrfToken(String token) {
        this.token = token;
    }

    @Override
    public String getHeaderName() {
        return "X-CSRF-TOKEN";
    }

    @Override
    public String getParameterName() {
        return "_csrf";
    }

    @Override
    public String getToken() {
        return token;
    }
}