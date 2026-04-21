package com.apratico.ragspringai.ingestion;

import com.apratico.ragspringai.config.RagProperties;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ChunkingServiceTest {

    private final RagProperties props = new RagProperties(4, 0.5, 500, "classpath:data/samples/*.md");
    private final ChunkingService chunker = new ChunkingService(props);

    @Test
    void split_producesChunks_andPreservesSourceMetadata() {
        String longText = "Manufacturing OEE is a composite KPI. ".repeat(400);
        Document input = new Document(longText, Map.of("source", "oee.md"));

        List<Document> chunks = chunker.split(List.of(input));

        assertThat(chunks).isNotEmpty();
        assertThat(chunks).allSatisfy(d ->
                assertThat(d.getMetadata()).containsEntry("source", "oee.md"));
        assertThat(chunks.stream().mapToInt(d -> d.getText().length()).sum())
                .isGreaterThan(0);
    }

    @Test
    void split_emptyInput_returnsEmpty() {
        List<Document> chunks = chunker.split(List.of());
        assertThat(chunks).isEmpty();
    }
}
