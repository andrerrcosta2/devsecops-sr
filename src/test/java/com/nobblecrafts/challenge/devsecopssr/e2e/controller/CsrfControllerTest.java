//package com.nobblecrafts.challenge.devsecopssr.e2e.controller;
//
//import com.nobblecrafts.challenge.devsecopssr.app.rest.MovieRestController;
//import com.nobblecrafts.challenge.devsecopssr.app.rest.UserActivityRestController;
//import com.nobblecrafts.challenge.devsecopssr.config.AbstractControllerTest;
//import com.nobblecrafts.challenge.devsecopssr.domain.CreateUserActivityHelper;
//import com.nobblecrafts.challenge.devsecopssr.domain.RegisterAccountHelper;
//import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.Account;
//import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieDetailsWithEvaluation;
//import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.RegisterAccountRequest;
//import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.TMDBMovie;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.test.context.support.WithMockUser;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@Slf4j
//@WithMockUser(username = "CsrfTestUser",
//        password = "Brasil2025$")
//class CsrfControllerTest extends AbstractControllerTest {
//
//    @Autowired
//    private MovieRestController movieRestController;
//
//
//    @BeforeAll
//    public static void setup(@Autowired RegisterAccountHelper registerAccountHelper,
//                             @Autowired CreateUserActivityHelper createUserActivityHelper) {
//        Account account = registerAccountHelper.persistAccount(RegisterAccountRequest.builder()
//                .username("CsrfTestUser")
//                .password("Brasil2025$")
//                .build());
//        createUserActivityHelper.initializeAndPersist(account);
//    }
//
//    @Test
//    void A00_shouldnt_Allow_Post_Put_Delete_Without_CSRF() throws Exception {
//        Optional<TMDBMovie> movie = movieRestController.getMoviesList(Optional.of(1)).getBody()
//                .stream().findAny();
//
//        assertTrue(movie.isPresent());
//
//        MovieDetailsWithEvaluation movieDetails = movieRestController
//                .getMovieDetailsEvaluated((long) movie.get().id()).getBody();
//
//
//        var result = mvc.perform(post(UserActivityRestController.PATH + "/favorite")
//                        .contentType("application/json")
//                        .content(convertEntityToJson(movieDetails))
//                        .with(request -> {
//                            request.removeParameter("_csrf");
//                            return request;
//                        }))
//
//                .andDo(print())
//                .andExpect(status().isForbidden())
//                .andReturn();
//
//        String jsonResponse = result.getResponse().getContentAsString();
//        log.info("JSON-RESPONSE: \n{}", prettifyJsonString(jsonResponse));
//    }
//
//    @Test
//    void A01_should_Allow_Post_Put_Delete_With_CSRF() throws Exception {
//        Optional<TMDBMovie> movie = movieRestController.getMoviesList(Optional.of(1)).getBody()
//                .stream().findAny();
//
//        assertTrue(movie.isPresent());
//
//        MovieDetailsWithEvaluation movieDetails = movieRestController
//                .getMovieDetailsEvaluated((long) movie.get().id()).getBody();
//
//
//        var result = mvc.perform(post(UserActivityRestController.PATH + "/want-to-watch")
//                        .with(csrf())
//                        .contentType("application/json")
//                        .content(convertEntityToJson(movieDetails)))
//
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful())
//                .andReturn();
//
//        String jsonResponse = result.getResponse().getContentAsString();
//        log.info("JSON-RESPONSE: \n{}", prettifyJsonString(jsonResponse));
//    }
//}
