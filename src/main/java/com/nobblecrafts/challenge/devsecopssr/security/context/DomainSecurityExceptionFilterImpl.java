package com.nobblecrafts.challenge.devsecopssr.security.context;

import com.nobblecrafts.challenge.devsecopssr.security.config.DomainSecurityConfigProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class DomainSecurityExceptionFilterImpl implements DomainSecurityExceptionFilter {

    private final UserDetailsService userDetailsService;
    private final DomainSecurityContext securityContext;
    private final DomainSecurityConfigProperties properties;
    @Override
    public void handleException(HttpServletRequest request, HttpServletResponse response) {
        validateSession();
    }

    private void validateSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Optional<UserDetails> currentUser = loadUserByUsername(authentication.getName());
            if (currentUser.isEmpty()) {
                securityContext.invalidateSession();
                return;
            }
        }
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
}
