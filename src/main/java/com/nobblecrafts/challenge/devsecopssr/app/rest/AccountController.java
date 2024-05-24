package com.nobblecrafts.challenge.devsecopssr.app.rest;

import com.nobblecrafts.challenge.devsecopssr.domain.AccountService;
import com.nobblecrafts.challenge.devsecopssr.domain.core.exception.DomainException;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.RegisterAccountRequest;
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
@RequiredArgsConstructor
@RequestMapping(path = "account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping(path = "register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String registerNewAccount(@ModelAttribute("registerAccountRequest") @Valid RegisterAccountRequest request,
                                                                      BindingResult result,
                                                                      Model model) {
        log.info("Trying to register: {}", request);

        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "register";
        }

        try {
            accountService.register(request);
        } catch (Exception ex) {
            log.info("Error registering account: {}", request);
            log.info("Exception: {}", ex.getMessage());
            model.addAttribute("errors", ex.getMessage());
            return "register";
        }

        model.addAttribute("message", "User registered successfully");

        return "redirect:/login";
    }
}
