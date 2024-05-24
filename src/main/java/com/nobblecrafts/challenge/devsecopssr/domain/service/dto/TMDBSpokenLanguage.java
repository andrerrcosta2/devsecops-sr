package com.nobblecrafts.challenge.devsecopssr.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record TMDBSpokenLanguage(
        @JsonProperty("english_name")
        String englishName,

        @JsonProperty("iso_639_1")
        String iso6391,

        @JsonProperty("name")
        String name
) {}
