package com.apratico.ragspringai.retrieval;

import com.apratico.ragspringai.config.RagProperties;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetrievalService {

    private final VectorStore vectorStore;
    private final RagProperties props;

    public RetrievalService(VectorStore vectorStore, RagProperties props) {
        this.vectorStore = vectorStore;
        this.props = props;
    }

    public List<Document> retrieve(String query, Integer topK) {
        int k = (topK != null && topK > 0) ? topK : props.topK();
        SearchRequest request = SearchRequest.builder()
                .query(query)
                .topK(k)
                .similarityThreshold(props.similarityThreshold())
                .build();
        List<Document> hits = vectorStore.similaritySearch(request);
        return hits != null ? hits : List.of();
    }
}
