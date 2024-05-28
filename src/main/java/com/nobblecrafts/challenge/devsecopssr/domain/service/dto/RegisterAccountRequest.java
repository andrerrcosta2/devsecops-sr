package com.nobblecrafts.challenge.devsecopssr.domain.service.dto;

import com.nobblecrafts.challenge.devsecopssr.security.validation.Alphanumeric;
import com.nobblecrafts.challenge.devsecopssr.security.validation.DomainValidAccountPassword;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder
public record RegisterAccountRequest(
        @NotEmpty(message = "The username is required")
        @Size(min = 5, max = 30, message = "The username must be between 5 and 30 characters")
        @Alphanumeric(message = "The username must be alphanumeric")
        String username,
        @DomainValidAccountPassword(message = "Password must be between 8 and 30 characters. Must contain " +
                "uppercase, lowercase, number and at least one special character")
        String password) {
}
