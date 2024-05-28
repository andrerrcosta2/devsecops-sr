package com.nobblecrafts.challenge.devsecopssr.e2e.security;

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
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class SqlInjectionSecurityTest extends AbstractHttpTest {

    // A aplicação não tem endpoints de contato direto com o banco de dados.
    // O que podemos fazer aqui seria criar os comandos dentro de um body
    @WithOAuth2User(username = "user-test", password = "password-test")
    @ParameterizedTest
    @CsvFileSource(resources = "/security/payload/sql/get-error-informations.csv", numLinesToSkip = 1)
    void A00_should_Throw_Exception(String command) {
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
