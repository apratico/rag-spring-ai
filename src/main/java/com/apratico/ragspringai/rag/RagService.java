package com.apratico.ragspringai.rag;

import com.apratico.ragspringai.retrieval.RetrievalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RagService {

    private static final Logger log = LoggerFactory.getLogger(RagService.class);

    private final RetrievalService retrieval;
    private final PromptBuilder promptBuilder;
    private final ChatClient chatClient;

    public RagService(RetrievalService retrieval, PromptBuilder promptBuilder, ChatClient chatClient) {
        this.retrieval = retrieval;
        this.promptBuilder = promptBuilder;
        this.chatClient = chatClient;
    }

    public RagAnswer answer(String question, Integer topK) {
        long start = System.currentTimeMillis();

        List<Document> retrieved = retrieval.retrieve(question, topK);
        log.debug("Retrieved {} chunks for question: {}", retrieved.size(), question);

        var prompt = promptBuilder.build(question, retrieved);
        String content = chatClient.prompt(prompt).call().content();

        List<Citation> citations = retrieved.stream()
                .map(d -> new Citation(
                        String.valueOf(d.getMetadata().getOrDefault("source", "unknown")),
                        d.getScore()
                ))
                .toList();

        long latency = System.currentTimeMillis() - start;
        return new RagAnswer(content, citations, latency);
    }

    public record RagAnswer(String answer, List<Citation> citations, long latencyMs) {
    }
}
