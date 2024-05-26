package com.nobblecrafts.challenge.devsecopssr.integration;

import com.nobblecrafts.challenge.devsecopssr.app.mvc.AuthController;
import com.nobblecrafts.challenge.devsecopssr.app.mvc.MovieController;
import com.nobblecrafts.challenge.devsecopssr.app.rest.MovieRestController;
import com.nobblecrafts.challenge.devsecopssr.config.AbstractControllerTest;
import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.MovieStatus;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("test")
@Slf4j
public class AuthorizationSecurityTest extends AbstractControllerTest {

    @Autowired
    private PasswordEncoder encoder;

    @Test
    @WithAnonymousUser
    void A00_should_Not_Authenticate_Wrong_Password() throws Exception {
        context.suppose(anAccount("test-user-joe", encoder.encode("Test-Password-2024")))
                .existsOnDatabase();

        MvcResult result = mvc.perform(post(AuthController.PATH + "/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("username", "test-user-joe")
                        .param("password", "wrong-password"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @WithAnonymousUser
    void A01_should_Authenticate_Correct_Password() throws Exception {
        context.suppose(anAccount("test-user-joe2", encoder
                        .encode("Test-Password-2024")))
                .existsOnDatabase();

        MvcResult result = mvc.perform(post(AuthController.PATH + "/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("username", "test-user-joe2")
                        .param("password", "Test-Password-2024"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andReturn();

    }

    @Test
    @WithAnonymousUser
    void A02_should_Not_Authorize_Secured_Requests_Anonymously() throws Exception {

        var result = mvc.perform(get(MovieController.PATH + "/favorite")
                        .contentType("application/json"))

                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();
    }
}
