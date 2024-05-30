package com.nobblecrafts.challenge.devsecopssr.config;

import com.nobblecrafts.challenge.devsecopssr.config.FeignConfig;
import com.nobblecrafts.challenge.devsecopssr.config.SecurityConfig;
import com.nobblecrafts.challenge.devsecopssr.config.SecurityFilterConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;

@Configuration
@Profile("test")
@Import({
        SecurityConfig.class,
        SecurityFilterConfig.class,
        FeignConfig.class,
})
public class GlobalTestContextConfiguration {
}
