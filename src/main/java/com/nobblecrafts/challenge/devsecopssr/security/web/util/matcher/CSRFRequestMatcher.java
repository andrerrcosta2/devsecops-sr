package com.nobblecrafts.challenge.devsecopssr.security.web.util.matcher;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;
import java.util.List;

public class CSRFRequestMatcher implements RequestMatcher {
    private static final List<String> PROTECTED_METHODS = Arrays.asList("POST", "PUT", "DELETE");
    @Override
    public boolean matches(HttpServletRequest request) {
        return PROTECTED_METHODS.contains(request.getMethod());
    }
}
