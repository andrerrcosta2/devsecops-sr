package com.nobblecrafts.challenge.devsecopssr.security.context;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public interface DomainSecurityExceptionFilter {
    void handleException(HttpServletRequest request, HttpServletResponse response);
}
