package com.nobblecrafts.challenge.devsecopssr.app.mvc;

import com.nobblecrafts.challenge.devsecopssr.domain.AccountService;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.RegisterAccountRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    public static final String PATH = "/account";
    private final AccountService accountService;

    @PostMapping(path = "register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String registerNewAccount(@ModelAttribute("registerAccountRequest") @Valid RegisterAccountRequest request,
                                     BindingResult result,
                                     Model model,
                                     HttpServletResponse response) {
        log.info("Trying to register: {}", request);

        if (result.hasErrors()) {
            log.info("Register request has errors: {}", result.getAllErrors());
            log.info("\n\nRAC: {}\n\n", request);
            model.addAttribute("errors", result.getAllErrors());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return "register";
        }

        try {
            accountService.register(request);
        } catch (Exception ex) {
            log.info("Error registering account: {}", request);
            log.info("Exception: {}", ex.getMessage());
            model.addAttribute("errors", ex.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return "register";
        }

        model.addAttribute("message", "User registered successfully");

        return "redirect:/login";
    }
}
