package com.apratico.ragspringai.ingestion;

import com.apratico.ragspringai.config.RagProperties;
import org.springframework.ai.document.Document;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class DocumentLoader {

    private final RagProperties props;
    private final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    public DocumentLoader(RagProperties props) {
        this.props = props;
    }

    public List<Document> loadSamples() throws IOException {
        Resource[] resources = resolver.getResources(props.samplesLocation());
        List<Document> docs = new ArrayList<>(resources.length);
        for (Resource r : resources) {
            String text = new String(r.getContentAsByteArray(), StandardCharsets.UTF_8);
            String source = r.getFilename() != null ? r.getFilename() : "unknown";
            docs.add(new Document(text, Map.of("source", source)));
        }
        return docs;
    }
}
