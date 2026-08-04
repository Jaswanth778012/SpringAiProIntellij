package com.kodnest.springaiprointellij.service;

import reactor.core.publisher.Flux;

public interface ChatService {

    public String chat(String q, String userId);

    public String chatTemplate();

    Flux<String> streamTemplate(String query);


}
