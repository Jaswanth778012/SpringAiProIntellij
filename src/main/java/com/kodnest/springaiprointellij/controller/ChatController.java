package com.kodnest.springaiprointellij.controller;

import com.kodnest.springaiprointellij.service.ChatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/AI")
public class ChatController {

    private ChatService chatService;

    public ChatController(ChatService chatService)
    {
        this.chatService = chatService;
    }

    @GetMapping("/Chat")
    public String chat(@RequestParam String q , @RequestHeader("userId") String userId)
    {
        return chatService.chat(q, userId);
    }

    @GetMapping("/ChatTemplate")
    public String chatTemplate()
    {
        return chatService.chatTemplate();
    }

    @GetMapping("/Stream-chat")
    public Flux<String> streamTemplate(@RequestParam("q") String query) {
        return chatService.streamTemplate(query);
    }
}
