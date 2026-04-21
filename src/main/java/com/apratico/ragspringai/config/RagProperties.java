package com.apratico.ragspringai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rag")
public record RagProperties(
        int topK,
        double similarityThreshold,
        int chunkSize,
        String samplesLocation
) {
}
