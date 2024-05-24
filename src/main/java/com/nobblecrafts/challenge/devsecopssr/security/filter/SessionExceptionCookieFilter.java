package com.nobblecrafts.challenge.devsecopssr.security.filter;

import com.nobblecrafts.challenge.devsecopssr.security.config.DomainSecurityConfigProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class SessionExceptionCookieFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final DomainSecurityConfigProperties properties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            if (userDetailsService.loadUserByUsername(username) == null) {
                SecurityContextHolder.clearContext();
                invalidateCookie(request, response);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session invalidated. Please log in again.");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void invalidateCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(properties.getCookieName(), null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // Expire the cookie
        response.addCookie(cookie);
    }
}
