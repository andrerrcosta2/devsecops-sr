package com.nobblecrafts.challenge.devsecopssr.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

// Essas classes também deveriam possuir constraints personalizadas
// de acodo com a resposta, pois são inputs
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
