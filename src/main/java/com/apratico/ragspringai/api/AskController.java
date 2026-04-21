package com.apratico.ragspringai.api;

import com.apratico.ragspringai.api.dto.AskRequest;
import com.apratico.ragspringai.api.dto.AskResponse;
import com.apratico.ragspringai.rag.RagService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ask")
public class AskController {

    private final RagService ragService;

    public AskController(RagService ragService) {
        this.ragService = ragService;
    }

    @PostMapping
    public AskResponse ask(@Valid @RequestBody AskRequest request) {
        RagService.RagAnswer answer = ragService.answer(request.question(), request.topK());
        return new AskResponse(
                request.question(),
                answer.answer(),
                answer.citations(),
                answer.latencyMs()
        );
    }
}
