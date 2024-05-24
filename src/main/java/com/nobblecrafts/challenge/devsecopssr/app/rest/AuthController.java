package com.nobblecrafts.challenge.devsecopssr.app.rest;

import com.nobblecrafts.challenge.devsecopssr.domain.AuthService;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.LoginRequest;
import com.nobblecrafts.challenge.devsecopssr.security.exception.AuthenticationException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String login(@ModelAttribute("loginRequest") @Valid LoginRequest request,
                        BindingResult result,
                        Model model,
                        HttpServletResponse response) {

        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "login";
        }

        try {
            authService.login(request, response);
        } catch (Exception e) {
            log.info("Error on login: {}", request);
            log.info("Exception: {}", e.getMessage());
            model.addAttribute("authError", true);
            return "login";
        }
        return "redirect:/home";
    }

}
