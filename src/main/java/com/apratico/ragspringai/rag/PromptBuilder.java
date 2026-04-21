package com.apratico.ragspringai.rag;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PromptBuilder {

    private final String template;

    public PromptBuilder(@Value("classpath:prompts/rag-system-prompt.st") Resource promptResource) {
        try {
            this.template = promptResource.getContentAsString(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load RAG prompt template", e);
        }
    }

    public Prompt build(String question, List<Document> context) {
        String joinedContext = context.stream()
                .map(this::renderSnippet)
                .collect(Collectors.joining("\n\n---\n\n"));
        if (joinedContext.isEmpty()) {
            joinedContext = "(no context retrieved)";
        }
        PromptTemplate pt = new PromptTemplate(template);
        return pt.create(Map.of(
                "context", joinedContext,
                "question", question
        ));
    }

    private String renderSnippet(Document doc) {
        String source = String.valueOf(doc.getMetadata().getOrDefault("source", "unknown"));
        return "[" + source + "]\n" + doc.getText();
    }
}
