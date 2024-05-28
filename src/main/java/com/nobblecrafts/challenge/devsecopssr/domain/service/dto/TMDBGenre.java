package com.nobblecrafts.challenge.devsecopssr.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

// Essas classes também deveriam possuir constraints personalizadas
// de acodo com a resposta, pois são inputs
@Builder
public record TMDBGenre(
        @JsonProperty("id")
        long id,

        @JsonProperty("name")
        String name
) {}
