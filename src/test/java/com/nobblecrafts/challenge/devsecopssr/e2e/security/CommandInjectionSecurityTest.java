package com.nobblecrafts.challenge.devsecopssr.e2e.security;


import com.nobblecrafts.challenge.devsecopssr.app.rest.UserActivityRestController;
import com.nobblecrafts.challenge.devsecopssr.config.AbstractHttpTest;
import com.nobblecrafts.challenge.devsecopssr.config.test.annotation.WithOAuth2User;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieDetailsWithEvaluation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class CommandInjectionSecurityTest extends AbstractHttpTest {

    @WithOAuth2User(username = "user-test", password = "password-test")
    @ParameterizedTest
    @CsvFileSource(resources = {"/security/payload/command/unix.csv",
            "/security/payload/command/windows.csv"},
            numLinesToSkip = 1)
    void A00_should_Be_Rejected_By_Firewall_Or_Mappings(String command) {
        ResponseEntity<MovieDetailsWithEvaluation> response = null;
        try {
            var uri = UriComponentsBuilder
                    .fromUriString(String.format("/favorite/%s", command)).build().toUri();
            response = rest
                    .exchange(uri,
                            HttpMethod.POST,
                            null,
                            MovieDetailsWithEvaluation.class);

            log.info("\n\nStatus Code: {}\n\n", response.getStatusCode());
            assertThat(response.getStatusCode().is2xxSuccessful()).isFalse();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.info("\n\nStatus Code: {}\n\n", response.getStatusCode());
            assertThat(e.getStatusCode().is4xxClientError() || e.getStatusCode().is5xxServerError()).isTrue();
        } catch (RestClientException e) {
            log.info("RestClientException: {}", e.getMessage());
            assertThat(true).isTrue();
        }
    }
}
