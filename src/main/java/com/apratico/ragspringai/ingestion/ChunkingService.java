package com.apratico.ragspringai.ingestion;

import com.apratico.ragspringai.config.RagProperties;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChunkingService {

    private final TokenTextSplitter splitter;

    public ChunkingService(RagProperties props) {
        this.splitter = new TokenTextSplitter(
                props.chunkSize(),
                350,
                5,
                10_000,
                true
        );
    }

    public List<Document> split(List<Document> docs) {
        return splitter.apply(docs);
    }
}
