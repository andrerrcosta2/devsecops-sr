package com.nobblecrafts.challenge.devsecopssr.security.filter;

import com.nobblecrafts.challenge.devsecopssr.security.context.DomainSecurityContext;
import com.nobblecrafts.challenge.devsecopssr.security.web.filter.WhitelistedOncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class SessionExceptionCookieFilter extends WhitelistedOncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final DomainSecurityContext securityContext;

    public SessionExceptionCookieFilter(Set<AntPathRequestMatcher> whiteList,
                           UserDetailsService userDetailsService,
                           DomainSecurityContext securityContext) {
        super(whiteList);
        this.userDetailsService = userDetailsService;
        this.securityContext = securityContext;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Optional<UserDetails> currentUser = loadUserByUsername(authentication.getName());
            if (currentUser.isEmpty()) {
                securityContext.invalidateSession();
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private Optional<UserDetails> loadUserByUsername(String username) {
        UserDetails output = null;
        try {
            output = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            log.info("Username not found", e.getMessage());
            return Optional.empty();
        } catch (Exception e) {
            log.error("Exception error: {}", e);
            return Optional.empty();
        }
        return Optional.ofNullable(output);
    }

    public static class SessionExceptionCookieFilterBuilder extends WhiteListedOncePerRequestFilterBuilder<SessionExceptionCookieFilter, SessionExceptionCookieFilterBuilder> {

        private UserDetailsService userDetailsService;
        private DomainSecurityContext securityContext;

        protected SessionExceptionCookieFilterBuilder() {
            super(new SessionExceptionCookieFilter(Collections.emptySet(), null, null)); // Initialize with an empty whiteList
        }

        public SessionExceptionCookieFilterBuilder userDetailsService(UserDetailsService userDetailsService) {
            this.userDetailsService = userDetailsService;
            return self();
        }

        public SessionExceptionCookieFilterBuilder securityContext(DomainSecurityContext securityContext) {
            this.securityContext = securityContext;
            return self();
        }

        @Override
        protected SessionExceptionCookieFilterBuilder self() {
            return this;
        }

        public SessionExceptionCookieFilter build() {
            return new SessionExceptionCookieFilter(whiteList, userDetailsService, securityContext);
        }
    }

    public static SessionExceptionCookieFilterBuilder builder() {
        return new SessionExceptionCookieFilterBuilder();
    }
}
