package com.nobblecrafts.challenge.devsecopssr.domain.service.dto;

import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.MovieStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder
public record MovieEvaluationCommand(
        @NotEmpty(message = "The movie id field is required")
        @Size(min = 1, max = 100, message = "The movie id must be between 1 and 100 characters")
        Long movieId,

        String currentUser,

        @NotNull(message = "The evaluation field is required")
        MovieStatus evaluation) {
}
