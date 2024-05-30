package com.nobblecrafts.challenge.devsecopssr.e2e.controller;

import com.nobblecrafts.challenge.devsecopssr.app.rest.MovieRestController;
import com.nobblecrafts.challenge.devsecopssr.app.rest.UserActivityRestController;
import com.nobblecrafts.challenge.devsecopssr.config.AbstractControllerTest;
import com.nobblecrafts.challenge.devsecopssr.dataaccess.account.entity.AccountEntity;
import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.MovieStatus;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieDetailsWithEvaluation;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.TMDBMovie;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static com.nobblecrafts.challenge.devsecopssr.util.EntitySupplier.anAccount;
import static com.nobblecrafts.challenge.devsecopssr.util.EntitySupplier.anUserActivity;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
class UserActivityTest extends AbstractControllerTest {
    @Autowired
    private MovieRestController movieRestController;

    @Test
    @WithMockUser(username = "testuser001",
            password = "Brasil2025$")
    void A00_should_Mark_Movie_As_Favorite() throws Exception {
        context.clear().suppose(anAccount("testuser001", "Brasil2025$"))
                .thenSuppose(context -> anUserActivity(context.retrieve(AccountEntity.class).get(0)))
                .existsOnDatabase();

        Optional<TMDBMovie> movie = movieRestController.getMoviesList(Optional.of(1)).getBody()
                .stream().findAny();

        assertTrue(movie.isPresent());

        MovieDetailsWithEvaluation mdwe = movieRestController
                .getMovieDetailsEvaluated((long) movie.get().id()).getBody();

        var result = mvc.perform(post(uri(UserActivityRestController.PATH,
                        "favorite",
                        String.valueOf(mdwe.movieDetails().id())))
                        .contentType("application/json")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.status", CoreMatchers.is(MovieStatus.FAVORITE.name())))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        log.info("JSON-RESPONSE: \n{}", prettifyJsonString(jsonResponse));
    }

    @Test
    @WithMockUser(username = "testuser002",
            password = "Brasil2025$")
    void A01_should_Mark_Movie_As_Want_to_Watch() throws Exception {
        context.clear().suppose(anAccount("testuser002", "Brasil2025$"))
                .thenSuppose(context -> anUserActivity(context.retrieve(AccountEntity.class).get(0)))
                .existsOnDatabase();

        Optional<TMDBMovie> movie = movieRestController.getMoviesList(Optional.of(1)).getBody()
                .stream().findAny();

        assertTrue(movie.isPresent());

        MovieDetailsWithEvaluation mdwe = movieRestController
                .getMovieDetailsEvaluated((long) movie.get().id()).getBody();

        var result = mvc.perform(post(uri(
                        UserActivityRestController.PATH,
                        "/want-to-watch",
                        String.valueOf(mdwe.movieDetails().id())))
                        .contentType("application/json")
                        .with(csrf()))

                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.status", CoreMatchers.is(MovieStatus.WANT_WATCH.name())))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        log.info("JSON-RESPONSE: \n{}", prettifyJsonString(jsonResponse));
    }

    @Test
    @WithMockUser(username = "testuser003",
            password = "Brasil2025$")
    void A02_should_Mark_Movie_As_Watched() throws Exception {
        context.clear().suppose(anAccount("testuser003", "Brasil2025$"))
                .thenSuppose(context -> anUserActivity(context.retrieve(AccountEntity.class).get(0)))
                .existsOnDatabase();

        Optional<TMDBMovie> movie = movieRestController.getMoviesList(Optional.of(1)).getBody()
                .stream().findAny();

        assertTrue(movie.isPresent());

        MovieDetailsWithEvaluation mdwe = movieRestController
                .getMovieDetailsEvaluated((long) movie.get().id()).getBody();

//        log.info("\nBefore call: {}\n", convertEntityToJson(movieDetails));

        var result = mvc.perform(post(uri(UserActivityRestController.PATH,
                        "watched",
                        String.valueOf(mdwe.movieDetails().id())))
                        .contentType("application/json")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.status", CoreMatchers.is(MovieStatus.WATCHED.name())))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        log.info("JSON-RESPONSE: \n{}", prettifyJsonString(jsonResponse));
    }

}
