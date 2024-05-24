package com.nobblecrafts.challenge.devsecopssr.integration;

import com.nobblecrafts.challenge.devsecopssr.config.AbstractControllerTest;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.RegisterAccountRequest;
import com.nobblecrafts.challenge.devsecopssr.util.SecretKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class AccountControllerTest extends AbstractControllerTest {

    private final String ENDPOINT = "/account";

    @Test
    void A00_should_Create_UserSuccessfully() throws Exception {

        SecretKeyGenerator.generate();

        var result = mvc.perform(post(ENDPOINT + "/register")
                        .contentType("application/json")
                        .content(convertEntityToJson(RegisterAccountRequest.builder()
                                .username("username001")
                                .password("Brasil2024$")
                                .build())))

                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    void A01_should_Throw_A_Bad_Request_For_Bad_Password() throws Exception {

        var result = mvc.perform(post(ENDPOINT + "/register")
                        .contentType("application/json")
                        .content(convertEntityToJson(RegisterAccountRequest.builder()
                                .username("username002")
                                .password("Brasil2024")
                                .build())))

                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        log.info("JSON-RESPONSE: \n{}", prettifyJsonString(jsonResponse));
    }

}
