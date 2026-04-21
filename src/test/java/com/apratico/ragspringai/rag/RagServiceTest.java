package com.apratico.ragspringai.rag;

import com.apratico.ragspringai.retrieval.RetrievalService;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RagServiceTest {

    @Test
    void answer_retrievesContext_buildsPrompt_callsChatClient_returnsCitations() {
        RetrievalService retrieval = mock(RetrievalService.class);
        PromptBuilder promptBuilder = mock(PromptBuilder.class);
        ChatClient chatClient = mock(ChatClient.class, RETURNS_DEEP_STUBS);

        Document hit1 = new Document("chunk-1 content", Map.of("source", "oee-kpi-iso22400.md"));
        hit1.getMetadata().put("distance", 0.12);
        Document hit2 = new Document("chunk-2 content", Map.of("source", "mes-vs-scada-isa95.md"));

        when(retrieval.retrieve("what is OEE?", 2)).thenReturn(List.of(hit1, hit2));

        Prompt prompt = new Prompt("stub");
        when(promptBuilder.build("what is OEE?", List.of(hit1, hit2))).thenReturn(prompt);
        when(chatClient.prompt(any(Prompt.class)).call().content()).thenReturn("OEE = A x P x Q.");

        RagService service = new RagService(retrieval, promptBuilder, chatClient);
        RagService.RagAnswer result = service.answer("what is OEE?", 2);

        assertThat(result.answer()).isEqualTo("OEE = A x P x Q.");
        assertThat(result.citations()).hasSize(2);
        assertThat(result.citations().get(0).source()).isEqualTo("oee-kpi-iso22400.md");
        assertThat(result.citations().get(1).source()).isEqualTo("mes-vs-scada-isa95.md");
        assertThat(result.latencyMs()).isGreaterThanOrEqualTo(0);

        verify(retrieval).retrieve("what is OEE?", 2);
        verify(promptBuilder).build("what is OEE?", List.of(hit1, hit2));
    }
}
