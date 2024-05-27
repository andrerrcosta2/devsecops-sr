package com.nobblecrafts.challenge.devsecopssr.e2e.security;

import com.nobblecrafts.challenge.devsecopssr.app.rest.MovieRestController;
import com.nobblecrafts.challenge.devsecopssr.app.rest.UserActivityRestController;
import com.nobblecrafts.challenge.devsecopssr.config.AbstractHttpTest;
import com.nobblecrafts.challenge.devsecopssr.config.test.annotation.WithOAuth2User;
import com.nobblecrafts.challenge.devsecopssr.config.test.security.web.csrf.TestCsrfToken;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieDetailsWithEvaluation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.web.csrf.CsrfToken;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class GeneralSecurityTest extends AbstractHttpTest {

    @Autowired
    private MovieRestController movieRestController;

    @Test
    @WithOAuth2User(username = "user-test", password = "password-test")
    void A00_shouldnt_Allow_Post_Put_Delete_Without_CSRF() {
        ResponseEntity<MovieDetailsWithEvaluation> response = rest
                .exchange(UserActivityRestController.PATH + "/favorite/823464",
                        HttpMethod.POST,
                        null,
                        MovieDetailsWithEvaluation.class);

        log.info("\n\nStatus Code: {}\n\n", response.getStatusCode());

        assertTrue(response.getStatusCode() == HttpStatus.UNAUTHORIZED);
    }

    @Test
    @WithOAuth2User(username = "user-test", password = "password-test")
    void A01_should_Allow_Post_Put_Delete_With_CSRF() {
        CsrfToken csrfToken = getCsrf();
        log.info("\n\nCSRF: {}\n\n", csrfToken);

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(csrfToken.getHeaderName(), csrfToken.getToken());
        headers.add(csrfToken.getParameterName(), csrfToken.getToken());
        headers.add("X-XSRF-TOKEN", csrfToken.getToken());

        // Create HttpEntity with headers
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<MovieDetailsWithEvaluation> response = rest
                .exchange(UserActivityRestController.PATH + "/favorite/823464",
                        HttpMethod.POST,
                        requestEntity,
                        MovieDetailsWithEvaluation.class);

        log.info("\n\nStatus Code: {}\n\n", response.getStatusCode());

        assertTrue(response.getStatusCode() == HttpStatus.OK);
    }

    private CsrfToken getCsrf() {
        ResponseEntity<Map<String, String>> response = rest.exchange(
                "/csrf",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, String>>() {
                }
        );

        String csrfToken = response.getBody().get("token");
        return new TestCsrfToken(csrfToken);
    }
}
