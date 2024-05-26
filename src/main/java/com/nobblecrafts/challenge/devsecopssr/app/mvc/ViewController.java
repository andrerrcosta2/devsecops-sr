package com.nobblecrafts.challenge.devsecopssr.app.mvc;

import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.LoginRequest;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.RegisterAccountRequest;
import com.nobblecrafts.challenge.devsecopssr.security.context.DomainSecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class ViewController {

    public static final String PATH = "/";
    private final DomainSecurityContext securityContext;

    @GetMapping
    public String getDefaultIndex() {
        return securityContext.isAuthenticated() ? "redirect:/home" : "redirect:/login";
    }

    @GetMapping("home")
    public String getDefaultHome() {
        return "redirect:/home/movies";
    }

    @GetMapping("login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", LoginRequest.builder().build());
        return "login";
    }

    @GetMapping("register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerAccountRequest", RegisterAccountRequest.builder().build());
        return "register";
    }
}
