package com.apratico.ragspringai.api;

import com.apratico.ragspringai.api.dto.IngestResponse;
import com.apratico.ragspringai.ingestion.IngestionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/ingest")
public class IngestController {

    private final IngestionService ingestionService;

    public IngestController(IngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public IngestResponse ingestFile(@RequestParam("file") MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename() != null ? file.getOriginalFilename() : "upload.txt";
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);
        int chunks = ingestionService.ingestText(filename, content);
        return new IngestResponse(filename, chunks);
    }

    @PostMapping("/samples")
    public IngestResponse reingestSamples() throws IOException {
        int chunks = ingestionService.ingestSamples();
        return new IngestResponse("classpath:data/samples/*.md", chunks);
    }
}
