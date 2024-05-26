package com.nobblecrafts.challenge.devsecopssr.integration;

import com.nobblecrafts.challenge.devsecopssr.app.rest.MovieRestController;
import com.nobblecrafts.challenge.devsecopssr.app.rest.UserActivityRestController;
import com.nobblecrafts.challenge.devsecopssr.config.AbstractControllerTest;
import com.nobblecrafts.challenge.devsecopssr.domain.CreateUserActivityHelper;
import com.nobblecrafts.challenge.devsecopssr.domain.RegisterAccountHelper;
import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.Account;
import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.MovieStatus;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieDetailsWithEvaluation;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.RegisterAccountRequest;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.TMDBMovie;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static com.nobblecrafts.challenge.devsecopssr.util.EntitySupplier.anAccount;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@WithMockUser(username = "testuser001",
        password = "Brasil2025$")
class UserActivityTest extends AbstractControllerTest {
    @Autowired
    private MovieRestController movieRestController;

    @Test
    void A00_should_Mark_Movie_As_Favorite() throws Exception {
        context.suppose(anAccount("test-user-joe", "Test-Password-2024"))
                .existsOnDatabase();

        Optional<TMDBMovie> movie = movieRestController.getMoviesList(Optional.of(1)).getBody()
                .stream().findAny();

        assertTrue(movie.isPresent());

        MovieDetailsWithEvaluation movieDetails = movieRestController
                .getMovieDetailsEvaluated((long) movie.get().id()).getBody();


        var result = mvc.perform(post(UserActivityRestController.PATH + "/favorite")
                        .contentType("application/json")
                        .content(convertEntityToJson(movieDetails)))

                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.status", CoreMatchers.is(MovieStatus.FAVORITE.name())))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        log.info("JSON-RESPONSE: \n{}", prettifyJsonString(jsonResponse));
    }

    @Test
    void A01_should_Mark_Movie_As_Want_to_Watch() throws Exception {
        Optional<TMDBMovie> movie = movieRestController.getMoviesList(Optional.of(1)).getBody()
                .stream().findAny();

        assertTrue(movie.isPresent());

        MovieDetailsWithEvaluation movieDetails = movieRestController
                .getMovieDetailsEvaluated((long) movie.get().id()).getBody();

//        log.info("\nBefore call: {}\n", convertEntityToJson(movieDetails));

        var result = mvc.perform(post(UserActivityRestController.PATH + "/want-to-watch")
                        .contentType("application/json")
                        .content(convertEntityToJson(movieDetails)))

                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.status", CoreMatchers.is(MovieStatus.WANT_WATCH.name())))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        log.info("JSON-RESPONSE: \n{}", prettifyJsonString(jsonResponse));
    }

    @Test
    void A02_should_Mark_Movie_As_Watched() throws Exception {
        Optional<TMDBMovie> movie = movieRestController.getMoviesList(Optional.of(1)).getBody()
                .stream().findAny();

        assertTrue(movie.isPresent());

        MovieDetailsWithEvaluation movieDetails = movieRestController
                .getMovieDetailsEvaluated((long) movie.get().id()).getBody();

//        log.info("\nBefore call: {}\n", convertEntityToJson(movieDetails));

        var result = mvc.perform(post(UserActivityRestController.PATH + "/watched")
                        .contentType("application/json")
                        .content(convertEntityToJson(movieDetails)))

                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.status", CoreMatchers.is(MovieStatus.WATCHED.name())))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        log.info("JSON-RESPONSE: \n{}", prettifyJsonString(jsonResponse));
    }

}
