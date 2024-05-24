package com.nobblecrafts.challenge.devsecopssr.security.model;

import lombok.Builder;
import lombok.Getter;

@Builder
public record TMDBAuthenticationResponse(Boolean success, Integer statusCode, String statusMessage) {
}
