package com.nobblecrafts.challenge.devsecopssr.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "devsecopsapp.security.api.keys")
public class AuthConfigProperties {
    String apiKey;
    String accessTokenAuth;
    String accountId;
}
