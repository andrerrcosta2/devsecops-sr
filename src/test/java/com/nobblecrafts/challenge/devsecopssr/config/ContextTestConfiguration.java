package com.nobblecrafts.challenge.devsecopssr.config;

import com.nobblecrafts.challenge.devsecopssr.config.test.context.ContextTransactionManager;
import com.nobblecrafts.challenge.devsecopssr.config.test.context.DatabaseContext;
import com.nobblecrafts.challenge.devsecopssr.config.test.core.io.PayloadArgumentsProvider;
import com.nobblecrafts.challenge.devsecopssr.config.test.filter.RequestContextForMvcRequestsFilter;
import com.nobblecrafts.challenge.devsecopssr.config.test.interceptor.OAuth2UserTestExecutionListener;
import com.nobblecrafts.challenge.devsecopssr.config.test.web.client.InterceptableTestRestTemplate;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@TestConfiguration
@Lazy
public class ContextTestConfiguration {

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

    @Bean
    public PayloadArgumentsProvider payloadArgumentsProvider(ResourceLoader resourceLoader,
                                                             @Value("${devsecops.test.security.payload:payloads/}") String payloads) {
        return new PayloadArgumentsProvider(resourceLoader, payloads);
    }
}
