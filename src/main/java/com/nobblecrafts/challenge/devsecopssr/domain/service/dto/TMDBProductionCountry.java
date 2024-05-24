package com.nobblecrafts.challenge.devsecopssr.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record TMDBProductionCountry(
        @JsonProperty("iso_3166_1")
        String iso31661,

        @JsonProperty("name")
        String name
) {}
