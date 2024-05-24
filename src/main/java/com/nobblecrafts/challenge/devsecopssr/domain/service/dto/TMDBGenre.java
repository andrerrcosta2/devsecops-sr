package com.nobblecrafts.challenge.devsecopssr.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record TMDBGenre(
        @JsonProperty("id")
        long id,

        @JsonProperty("name")
        String name
) {}
