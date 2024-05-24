package com.nobblecrafts.challenge.devsecopssr.domain.service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder
public record LoginRequest(
        @NotEmpty(message = "The username field is required")
        @Size(min = 5, max = 30, message = "The username must be between 5 and 30 characters")
        String username,

        @NotEmpty(message = "The password field is required")
        @Size(min = 5, max = 20, message = "The password must be between 8 and 20 characters")
        String password) {
}
