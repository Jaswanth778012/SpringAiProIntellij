package com.kodnest.springaiprointellij.service;

import reactor.core.publisher.Flux;

import java.util.List;

public interface ChatService {

    public String chat(String q, String userId);

    public String chatTemplate();

    Flux<String> streamTemplate(String query);

    void savedData(List<String> list);

    public String chatQ(String q, String conversation);


}
