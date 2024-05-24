package com.nobblecrafts.challenge.devsecopssr.config;

import com.nobblecrafts.challenge.devsecopssr.security.config.DomainSecurityConfigProperties;
import com.nobblecrafts.challenge.devsecopssr.security.filter.JwtCookieFilter;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;

@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfig {

    private final DomainSecurityConfigProperties properties;

    @Bean
    public JwtCookieFilter jwtCookieFilter() {
        return new JwtCookieFilter(properties);
    }

    @Bean
    public FilterRegistrationBean<JwtCookieFilter> jwtCookieFilterRegistration(JwtCookieFilter filter) {
        FilterRegistrationBean<JwtCookieFilter> registrationBean = new FilterRegistrationBean<>(filter);
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }
}
