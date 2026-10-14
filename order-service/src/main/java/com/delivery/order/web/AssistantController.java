package com.delivery.order.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.order.error.BadRequestException;
import com.delivery.order.service.ChatService;

@RestController
public class AssistantController {

    private final ChatService chatService;

    public AssistantController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/assistant")
    public AssistantResponse ask(@RequestBody AssistantRequest request) {
        if (request.question() == null || request.question().isBlank()) {
            throw new BadRequestException("Question is required");
        }
        return new AssistantResponse(chatService.answer(request.question()));
    }
}
