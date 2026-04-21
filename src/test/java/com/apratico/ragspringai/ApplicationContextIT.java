package com.apratico.ragspringai;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@TestPropertySource(properties = {
        "spring.docker.compose.enabled=false",
        "spring.ai.openai.api-key=test-dummy-key",
        "rag.bootstrap-enabled=false"
})
class ApplicationContextIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("pgvector/pgvector:pg16")
                    .withDatabaseName("ragdb")
                    .withUsername("rag")
                    .withPassword("ragpass");

    @MockBean
    EmbeddingModel embeddingModel;

    @MockBean
    ChatModel chatModel;

    @Autowired
    MockMvc mvc;

    @Test
    void contextLoads_flywayRuns_actuatorHealthy() throws Exception {
        when(embeddingModel.dimensions()).thenReturn(1536);
        when(embeddingModel.embed(any(String.class))).thenReturn(new float[1536]);
        when(embeddingModel.call(any(EmbeddingRequest.class))).thenAnswer(inv -> null);
        when(chatModel.call(any(Prompt.class))).thenAnswer(inv -> new ChatResponse(java.util.List.of()));

        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/actuator/health"))
                .andExpect(status().isOk());
    }
}
