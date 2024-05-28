package com.nobblecrafts.challenge.devsecopssr.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

// Essas classes também deveriam possuir constraints personalizadas
// de acodo com a resposta, pois são inputs
@Builder
public record TMDBSpokenLanguage(
        @JsonProperty("english_name")
        String englishName,

        @JsonProperty("iso_639_1")
        String iso6391,

        @JsonProperty("name")
        String name
) {}
