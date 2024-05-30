package com.nobblecrafts.challenge.devsecopssr.e2e.controller;

import com.nobblecrafts.challenge.devsecopssr.app.rest.MovieRestController;
import com.nobblecrafts.challenge.devsecopssr.config.AbstractControllerTest;
import com.nobblecrafts.challenge.devsecopssr.dataaccess.account.entity.AccountEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import static com.nobblecrafts.challenge.devsecopssr.util.EntitySupplier.anAccount;
import static com.nobblecrafts.challenge.devsecopssr.util.EntitySupplier.anUserActivity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class MovieControllerTest extends AbstractControllerTest {

    @Test
    @DisplayName("Listar filmes")
    @WithMockUser(username = "testuser001",
            password = "Brasil2025$")
    void A00_list_movies() throws Exception {
        context.clear().suppose(anAccount("testuser001", "Brasil2025$"))
                .thenSuppose(context -> anUserActivity(context.retrieve(AccountEntity.class).get(0)))
                .existsOnDatabase();

        MvcResult result = mvc.perform(get(MovieRestController.PATH + "/1")
                        .content("application/json"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        log.info("JSON-RESPONSE: \n{}", jsonResponse);
    }
}
