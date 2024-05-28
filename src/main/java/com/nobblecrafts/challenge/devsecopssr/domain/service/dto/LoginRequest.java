package com.nobblecrafts.challenge.devsecopssr.domain.service.dto;

import com.nobblecrafts.challenge.devsecopssr.security.validation.Alphanumeric;
import com.nobblecrafts.challenge.devsecopssr.security.validation.DomainValidAccountPassword;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.apache.commons.lang3.StringEscapeUtils;

@Builder
public record LoginRequest(
        @NotEmpty(message = "The username field is required")
        @Size(min = 5, max = 30, message = "The username must be between 5 and 30 characters")
        @Alphanumeric(message = "The username must be alphanumeric")
        String username,

        @NotEmpty(message = "The password field is required")
        @Size(min = 5, max = 30, message = "The password must be between 8 and 20 characters")
        @DomainValidAccountPassword(message = "invalid password")
        String password) {
}
