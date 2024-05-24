package com.nobblecrafts.challenge.devsecopssr.security.filter;

import com.nobblecrafts.challenge.devsecopssr.security.config.DomainSecurityConfigProperties;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

@Slf4j
@RequiredArgsConstructor
//@WebFilter("/*")
public class JwtCookieFilter extends OncePerRequestFilter {

    private final DomainSecurityConfigProperties properties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (properties.getCookieName().equals(cookie.getName())) {
                    String token = cookie.getValue();
                    HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request) {
                        @Override
                        public String getHeader(String name) {
                            if (HttpHeaders.AUTHORIZATION.equals(name)) {
                                return "Bearer " + token;
                            }
                            return super.getHeader(name);
                        }

                        @Override
                        public Enumeration<String> getHeaders(String name) {
                            if (HttpHeaders.AUTHORIZATION.equals(name)) {
                                return Collections.enumeration(Collections.singletonList("Bearer " + token));
                            }
                            return super.getHeaders(name);
                        }
                    };
                    filterChain.doFilter(requestWrapper, response);
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

}
