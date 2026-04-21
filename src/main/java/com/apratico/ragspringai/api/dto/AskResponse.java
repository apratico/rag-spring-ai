package com.apratico.ragspringai.api.dto;

import com.apratico.ragspringai.rag.Citation;

import java.util.List;

public record AskResponse(
        String question,
        String answer,
        List<Citation> citations,
        long latencyMs
) {
}
