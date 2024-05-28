package com.nobblecrafts.challenge.devsecopssr.security.web.util.matcher;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@Slf4j
public class CSRFRequestMatcher implements RequestMatcher {
    private static final List<String> PROTECTED_METHODS = Arrays.asList("POST", "PUT", "DELETE");

    @Override
    public boolean matches(HttpServletRequest request) {
//        log.info("\n\nCSRF Request Matcher: {}:{}\n\n", request.getMethod(), request.getPathInfo());
//        log.info("Request Headers:");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
//            log.info("\n\n{}: {}\n\n", headerName, headerValue);
        }
//        log.info("\n\nShould match? {}\n\n", PROTECTED_METHODS.contains(request.getMethod()));
        return PROTECTED_METHODS.contains(request.getMethod());
    }
}
