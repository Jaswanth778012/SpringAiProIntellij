package com.kodnest.springaiprointellij.service;

import org.jspecify.annotations.Nullable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
public class ChatServiceImpl implements ChatService{

    private final ChatClient chatClient;

    @Value("classpath:prompt/userPrompt.st")
    private Resource userPrompt;

    public ChatServiceImpl(ChatClient chatClient)
    {
        super();
        this.chatClient = chatClient;
    }

    public String chat(String q, String userId) {
        String queryString = "You give five {topic} Concepts";
       return  chatClient.prompt()
               .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, userId))
                .system("You are an expert in product suggestion and recommendation.")
                .user(user -> user.text(userPrompt).param("Topic", q))
                .call()
                .content();
    }


    public String chatTemplate()
    {
//        PromptTemplate userPrompt = PromptTemplate.builder().template("What is {techName}? and give me the example of {example}").build();
//
//        Message userMessage = userPrompt.createMessage(Map.of(
//                "techName", "Java",
//                "example", "Springboot"
//        ));
//
//        PromptTemplate SystemPrompt = PromptTemplate.builder().template("You are an expert in product suggestion and recommendation. Please provide a detailed and personalized product recommendation").build();
//
//        Message systemMessage = SystemPrompt.createMessage();
//
//        Prompt p = new Prompt(userMessage, systemMessage);

//        @Nullable String gs = chatClient.prompt(p).call().content();

//        return gs;

        return this.chatClient
                .prompt()
                .system("You are an Expert in Coding")
                .user(user->user.text(userPrompt).param("Topic", "Java"))
                .call()
                .content();

    }

    @Override
    public Flux<String> streamTemplate(String query) {

        return this.chatClient
                .prompt()
                .system("You are an Expert in coding. Explain in detail woith example")
                .user(user -> user.text(this.userPrompt).param("Topic", query))
                .stream()
                .content();
    }

}
