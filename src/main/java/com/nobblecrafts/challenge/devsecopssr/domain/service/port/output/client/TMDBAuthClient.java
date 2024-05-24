package com.nobblecrafts.challenge.devsecopssr.domain.service.port.output.client;

import com.nobblecrafts.challenge.devsecopssr.config.FeignConfig;
import com.nobblecrafts.challenge.devsecopssr.security.model.TMDBAuthenticationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(name = "tmdb-auth-client",
        url = "https://api.themoviedb.org",
        path = "/3/authentication",
        configuration = FeignConfig.class)
public interface TMDBAuthClient {
    @GetMapping()
    TMDBAuthenticationResponse authenticate();
}
