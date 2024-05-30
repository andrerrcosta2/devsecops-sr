package com.nobblecrafts.challenge.devsecopssr.e2e.controller;

import com.nobblecrafts.challenge.devsecopssr.app.mvc.AuthController;
import com.nobblecrafts.challenge.devsecopssr.app.mvc.MovieController;
import com.nobblecrafts.challenge.devsecopssr.config.AbstractControllerTest;
import com.nobblecrafts.challenge.devsecopssr.config.SecurityConfig;
import com.nobblecrafts.challenge.devsecopssr.config.SecurityFilterConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MvcResult;

import static com.nobblecrafts.challenge.devsecopssr.util.EntitySupplier.anAccount;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("test")
@Slf4j
@WithAnonymousUser
class AuthorizationSecurityTest extends AbstractControllerTest {

    @Autowired
    private PasswordEncoder encoder;

    @Test
    void A00_should_Not_Authenticate_Wrong_Password() throws Exception {
        context.clear()
                .suppose(anAccount("TestUserJoe", encoder.encode("TestPassword2024$")))
                .existsOnDatabase();

        MvcResult result = mvc.perform(post(AuthController.PATH + "/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("username", "TestUserJoe")
                        .param("password", "wrongPassword1$"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void A01_should_Authenticate_Correct_Password() throws Exception {
        context.clear().suppose(anAccount("TestUserJoey", encoder
                        .encode("TestPassword2024$")))
                .existsOnDatabase();

        MvcResult result = mvc.perform(post(AuthController.PATH + "/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("username", "TestUserJoey")
                        .param("password", "TestPassword2024$"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @Test
    void A02_should_Not_Authorize_Secured_Requests_Anonymously() throws Exception {
        mvc.perform(get(uri(MovieController.PATH))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }
}
