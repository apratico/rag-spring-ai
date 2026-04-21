package com.apratico.ragspringai.ingestion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class IngestionService {

    private static final Logger log = LoggerFactory.getLogger(IngestionService.class);

    private final DocumentLoader loader;
    private final ChunkingService chunker;
    private final VectorStore vectorStore;

    public IngestionService(DocumentLoader loader,
                            ChunkingService chunker,
                            VectorStore vectorStore) {
        this.loader = loader;
        this.chunker = chunker;
        this.vectorStore = vectorStore;
    }

    public int ingestSamples() throws IOException {
        List<Document> raw = loader.loadSamples();
        log.info("Loaded {} sample documents", raw.size());
        List<Document> chunks = chunker.split(raw);
        log.info("Split into {} chunks", chunks.size());
        vectorStore.add(chunks);
        log.info("Indexed {} chunks into vector store", chunks.size());
        return chunks.size();
    }

    public int ingestText(String source, String content) {
        Document doc = new Document(content, Map.of("source", source));
        List<Document> chunks = chunker.split(List.of(doc));
        vectorStore.add(chunks);
        log.info("Indexed {} chunks from '{}'", chunks.size(), source);
        return chunks.size();
    }
}
