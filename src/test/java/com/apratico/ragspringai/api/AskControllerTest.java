package com.apratico.ragspringai.api;

import com.apratico.ragspringai.rag.Citation;
import com.apratico.ragspringai.rag.RagService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AskController.class)
class AskControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RagService ragService;

    private final ObjectMapper json = new ObjectMapper();

    @Test
    void ask_happyPath_returns200AndBody() throws Exception {
        RagService.RagAnswer answer = new RagService.RagAnswer(
                "OEE multiplies Availability, Performance, Quality.",
                List.of(new Citation("oee-kpi-iso22400.md", 0.87)),
                42L
        );
        when(ragService.answer(eq("what is OEE?"), any())).thenReturn(answer);

        String body = json.writeValueAsString(Map.of("question", "what is OEE?"));

        mvc.perform(post("/ask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question").value("what is OEE?"))
                .andExpect(jsonPath("$.answer").value("OEE multiplies Availability, Performance, Quality."))
                .andExpect(jsonPath("$.citations[0].source").value("oee-kpi-iso22400.md"))
                .andExpect(jsonPath("$.latencyMs").value(42));
    }

    @Test
    void ask_blankQuestion_returns400() throws Exception {
        String body = json.writeValueAsString(Map.of("question", ""));

        mvc.perform(post("/ask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("ValidationFailed"));
    }
}
