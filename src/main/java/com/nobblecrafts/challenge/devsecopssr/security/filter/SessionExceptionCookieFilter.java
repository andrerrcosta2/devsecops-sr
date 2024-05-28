package com.nobblecrafts.challenge.devsecopssr.security.filter;

import com.nobblecrafts.challenge.devsecopssr.security.context.DomainSecurityContext;
import com.nobblecrafts.challenge.devsecopssr.security.context.DomainSecurityExceptionFilter;
import com.nobblecrafts.challenge.devsecopssr.security.web.filter.WhitelistedOncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

/**
 * Esse filtro não é realmente necessário para uma aplicação onde o gerenciamento
 * de sessão é stateless. Nesse caso ele faz requisições repetidas para o banco de
 * dados, algo que em uma sessão persistida é feito geralmente em cache.
 * Mas é um filtro que eu costumo criar para lidar com exceções de segurança customizadas
 * para aplicações com necessidades diferentes.
 */
@Slf4j
@RequiredArgsConstructor
public class SessionExceptionCookieFilter extends WhitelistedOncePerRequestFilter {

    private final DomainSecurityExceptionFilter sef;

    public SessionExceptionCookieFilter(Set<AntPathRequestMatcher> whiteList,
                           DomainSecurityExceptionFilter sef) {
        super(whiteList);
        this.sef = sef;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        sef.handleException(request, response);
        filterChain.doFilter(request, response);
    }

    public static class SessionExceptionCookieFilterBuilder extends WhiteListedOncePerRequestFilterBuilder<SessionExceptionCookieFilter, SessionExceptionCookieFilterBuilder> {

        private DomainSecurityExceptionFilter sef;

        protected SessionExceptionCookieFilterBuilder() {
            super(new SessionExceptionCookieFilter(Collections.emptySet(), null)); // Initialize with an empty whiteList
        }

        public SessionExceptionCookieFilterBuilder domanSecurityExceptionFilter(DomainSecurityExceptionFilter sef) {
            this.sef = sef;
            return self();
        }


        @Override
        protected SessionExceptionCookieFilterBuilder self() {
            return this;
        }

        public SessionExceptionCookieFilter build() {
            return new SessionExceptionCookieFilter(whiteList, sef);
        }
    }

    public static SessionExceptionCookieFilterBuilder builder() {
        return new SessionExceptionCookieFilterBuilder();
    }
}
