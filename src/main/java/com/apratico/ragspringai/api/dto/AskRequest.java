package com.apratico.ragspringai.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record AskRequest(
        @NotBlank(message = "question must not be blank") String question,
        @Min(value = 1, message = "topK must be >= 1") Integer topK
) {
}
