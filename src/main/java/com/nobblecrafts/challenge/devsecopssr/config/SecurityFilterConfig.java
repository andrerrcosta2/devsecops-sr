package com.nobblecrafts.challenge.devsecopssr.config;

import com.nobblecrafts.challenge.devsecopssr.security.config.DomainSecurityConfigProperties;
import com.nobblecrafts.challenge.devsecopssr.security.context.DomainSecurityContext;
import com.nobblecrafts.challenge.devsecopssr.security.context.DomainSecurityExceptionFilter;
import com.nobblecrafts.challenge.devsecopssr.security.context.DomainSecurityExceptionFilterImpl;
import com.nobblecrafts.challenge.devsecopssr.security.filter.JwtCookieFilter;
import com.nobblecrafts.challenge.devsecopssr.security.filter.SessionExceptionCookieFilter;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;

@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfig {

    @Bean
    public JwtCookieFilter jwtCookieFilter(DomainSecurityConfigProperties properties) {
        return JwtCookieFilter.builder()
                .securityConfigProperties(properties)
                .addToWhiteList(HttpMethod.POST, "/auth/login")
                .addToWhiteList(HttpMethod.GET, "/register")
                .addToWhiteList(HttpMethod.GET, "/auth/login")
                .addToWhiteList(HttpMethod.POST, "/account/register")
                .addToWhiteList(HttpMethod.OPTIONS, "/**")
                .addToWhiteList(HttpMethod.GET, "/")
                .build();
    }

    @Bean
    public SessionExceptionCookieFilter sessionExceptionCookieFilter(DomainSecurityExceptionFilter sef) {
        return SessionExceptionCookieFilter.builder()
                .domanSecurityExceptionFilter(sef)
                .addToWhiteList(HttpMethod.POST, "/auth/login")
                .addToWhiteList(HttpMethod.GET, "/register")
                .addToWhiteList(HttpMethod.GET, "/auth/login")
                .addToWhiteList(HttpMethod.POST, "/account/register")
                .addToWhiteList(HttpMethod.OPTIONS, "/**")
                .addToWhiteList(HttpMethod.GET, "/")
                .build();
    }

    @Bean
    public DomainSecurityExceptionFilter domainSecurityExceptionFilter(UserDetailsService userDetailsService,
                                                                       DomainSecurityContext securityContext,
                                                                       DomainSecurityConfigProperties properties) {
        return new DomainSecurityExceptionFilterImpl(userDetailsService, securityContext, properties);
    }

    @Bean
    public FilterRegistrationBean<JwtCookieFilter> jwtCookieFilterRegistration(JwtCookieFilter filter) {
        FilterRegistrationBean<JwtCookieFilter> registrationBean = new FilterRegistrationBean<>(filter);
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<SessionExceptionCookieFilter> sessionExceptionCookieFilterFilterRegistration(SessionExceptionCookieFilter filter) {
        FilterRegistrationBean<SessionExceptionCookieFilter> registrationBean = new FilterRegistrationBean<>(filter);
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registrationBean;
    }
}
