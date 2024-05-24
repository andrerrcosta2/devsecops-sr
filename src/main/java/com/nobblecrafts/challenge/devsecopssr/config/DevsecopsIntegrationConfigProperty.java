package com.nobblecrafts.challenge.devsecopssr.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "devsecopsapp.integration.api")
public class DevsecopsIntegrationConfigProperty {
    private String tmdb;
    private String tmdbAuthPath;
    private String tmdbAccountPath;
}
