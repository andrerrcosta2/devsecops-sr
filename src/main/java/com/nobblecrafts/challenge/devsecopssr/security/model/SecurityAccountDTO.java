package com.nobblecrafts.challenge.devsecopssr.security.model;

import lombok.Builder;

@Builder
public record SecurityAccountDTO(Long id, String principal) {}
