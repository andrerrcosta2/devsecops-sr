package com.nobblecrafts.challenge.devsecopssr.config;

import com.nobblecrafts.challenge.devsecopssr.domain.core.config.FeignGlobalError;
import com.nobblecrafts.challenge.devsecopssr.security.interceptor.FeignClientSecurityInterceptor;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@EnableFeignClients(basePackages = "com.nobblecrafts.challenge.devsecopssr.domain.service.port.output.client")
@ImportAutoConfiguration(FeignAutoConfiguration.class)
public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    @Lazy
    public FeignGlobalError feignGlobalErrorHandler() {
        return new FeignGlobalError();
    }

    @Bean
    @Lazy
    public RequestInterceptor feignClientSecurityInterceptor(AuthConfigProperties property) {
        return new FeignClientSecurityInterceptor(property);
    }
}
