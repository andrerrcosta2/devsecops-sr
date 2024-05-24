package com.nobblecrafts.challenge.devsecopssr.integration;

import com.nobblecrafts.challenge.devsecopssr.config.AbstractControllerTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WithMockUser(username = "testuser001",
        password = "Brasil2025$")
class MovieControllerTest extends AbstractControllerTest {
    private final String ENDPOINT = "/movies";

    @Test
    @DisplayName("Listar filmes")
    void A00_list_movies() throws Exception {
        MvcResult result = mvc.perform(get(ENDPOINT + "/1").content("application/json"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        log.info("JSON-RESPONSE: \n{}", jsonResponse);
    }
}
