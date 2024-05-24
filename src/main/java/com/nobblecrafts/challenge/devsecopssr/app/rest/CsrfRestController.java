package com.nobblecrafts.challenge.devsecopssr.app.rest;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "csrf", produces = "application/vnd.api.v1+json")
public class CsrfRestController {
    @GetMapping("token")
    public CsrfToken getToken(CsrfToken token) {
        return token;
    }

}
