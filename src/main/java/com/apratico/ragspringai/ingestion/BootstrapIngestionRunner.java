package com.apratico.ragspringai.ingestion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "rag.bootstrap-enabled", havingValue = "true", matchIfMissing = true)
public class BootstrapIngestionRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(BootstrapIngestionRunner.class);

    private final IngestionService ingestion;
    private final VectorStore vectorStore;

    public BootstrapIngestionRunner(IngestionService ingestion, VectorStore vectorStore) {
        this.ingestion = ingestion;
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (isStoreEmpty()) {
            log.info("Vector store is empty — running bootstrap ingestion of sample documents");
            ingestion.ingestSamples();
        } else {
            log.info("Vector store already populated — skipping bootstrap ingestion");
        }
    }

    private boolean isStoreEmpty() {
        try {
            return vectorStore.similaritySearch(
                    SearchRequest.builder().query("probe").topK(1).build()
            ).isEmpty();
        } catch (Exception e) {
            log.warn("Vector store probe failed; assuming empty store. Cause: {}", e.getMessage());
            return true;
        }
    }
}
