package com.nobblecrafts.challenge.devsecopssr.security.interceptor;

import com.nobblecrafts.challenge.devsecopssr.config.AuthConfigProperties;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
public class FeignClientSecurityInterceptor implements RequestInterceptor {

    private final AuthConfigProperties property;

    @Override
    public void apply(RequestTemplate template) {
        log.info("Attempting to request to api: {}", template.url());
        template.header(HttpHeaders.AUTHORIZATION, String.format("%s %s", "Bearer ", property.getAccessTokenAuth()));
    }
}
