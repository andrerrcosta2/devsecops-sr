package com.nobblecrafts.challenge.devsecopssr.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record TMDBProductionCompany(
        @JsonProperty("id")
        Long id,

        @JsonProperty("logo_path")
        String logoPath,

        @JsonProperty("name")
        String name,

        @JsonProperty("origin_country")
        String originCountry
) {}
