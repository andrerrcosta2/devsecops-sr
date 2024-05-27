package com.nobblecrafts.challenge.devsecopssr.e2e.controller;

import com.nobblecrafts.challenge.devsecopssr.app.mvc.AccountController;
import com.nobblecrafts.challenge.devsecopssr.config.AbstractControllerTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;

import static com.nobblecrafts.challenge.devsecopssr.util.EntitySupplier.anAccount;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WithAnonymousUser
class AccountControllerTest extends AbstractControllerTest {

    @Test
    void A00_should_Create_User_Successfully() throws Exception {

        var result = mvc.perform(post(AccountController.PATH + "/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "test-user-joe")
                        .param("password", "Good-Pa$$word-2024"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @Test
    void A01_should_Throw_A_Bad_Request_For_Bad_Password() throws Exception {

        var result = mvc.perform(post(AccountController.PATH + "/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "test-user-joe")
                        .param("password", "bad-password-2024"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
            }

    @Test
    void A02_should_Not_Allow_Create_Account_With_Existing_Username() throws Exception {
        context.suppose(anAccount("test-user-joe2", "Test-Password-2024"))
                .existsOnDatabase();

        mvc.perform(post(AccountController.PATH + "/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "test-user-joe2")
                        .param("password", "Good-Pa$$word-2024"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

    }
}
