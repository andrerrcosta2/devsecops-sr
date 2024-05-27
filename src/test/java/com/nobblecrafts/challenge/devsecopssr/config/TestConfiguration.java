package com.nobblecrafts.challenge.devsecopssr.config;

import com.nobblecrafts.challenge.devsecopssr.config.test.context.ContextTransactionManager;
import com.nobblecrafts.challenge.devsecopssr.config.test.interceptor.OAuth2UserTestExecutionListener;
import com.nobblecrafts.challenge.devsecopssr.config.test.web.client.InterceptableTestRestTemplate;
import com.nobblecrafts.challenge.devsecopssr.config.test.context.DatabaseContext;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
@Lazy
public class TestConfiguration {

    @LocalServerPort
    private int port;

    @Bean
    public OAuth2UserTestExecutionListener listener() {
        return new OAuth2UserTestExecutionListener();
    }

    @Bean
    public InterceptableTestRestTemplate restTemplate(RestTemplateBuilder rtb) {
        return new InterceptableTestRestTemplate(rtb, "http://localhost", port);
    }

    @Bean
    public ContextTransactionManager contextTransactionManager(EntityManagerFactory emf) {
        return new ContextTransactionManager(emf);
    }

    @Bean
    public DatabaseContext databaseContext(ContextTransactionManager ctm) {
        return new DatabaseContext(ctm);
    }
}
