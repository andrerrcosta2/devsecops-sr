package com.nobblecrafts.challenge.devsecopssr.domain.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

// Essas classes também deveriam possuir constraints personalizadas
// de acodo com a resposta, pois são inputs
@Builder
public record TMDBPage<T>(
        int page,
        List<T>results,
        int totalPages,
        int totalResults) {
}
