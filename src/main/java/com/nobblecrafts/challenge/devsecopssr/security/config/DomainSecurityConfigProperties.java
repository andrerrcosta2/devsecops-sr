package com.nobblecrafts.challenge.devsecopssr.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Data
@Configuration
@ConfigurationProperties(prefix = "devsecopsapp.security", ignoreUnknownFields = true)
public class DomainSecurityConfigProperties {
    Set<String> allowedOrigins;
    Set<String> allowedHeaders;
    String cookieName;
    int cookieMaxAge;
    String cookieDomain;
    boolean restrictCookieToHttps;
}
