package com.nobblecrafts.challenge.devsecopssr.app.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
public record ErrorDTO(String code, String message) {}
