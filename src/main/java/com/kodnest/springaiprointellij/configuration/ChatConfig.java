package com.kodnest.springaiprointellij.configuration;

import com.kodnest.springaiprointellij.advisors.TokenPrintAdvisor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class ChatConfig {
    Logger logger = LoggerFactory.getLogger(ChatConfig.class);

//    @Bean
//    public ChatMemory chatMemo(JdbcChatMemoryRepository repo) {
//        return MessageWindowChatMemory.builder()
//                .chatMemoryRepository(repo)
//                .maxMessages(10)
//                .build();
//    }

    @Bean ChatMemory chatMemory() {
        InMemoryChatMemoryRepository inMemoryChatMemoryRepository = new InMemoryChatMemoryRepository();
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(inMemoryChatMemoryRepository)
                .maxMessages(10)
                .build();
    }
    @Bean
    public ChatClient chat(ChatClient.Builder builder, ChatMemory memory)
    {
        logger.info(memory.getClass().getName());

        MessageChatMemoryAdvisor messageMemory = MessageChatMemoryAdvisor.builder(memory).build();

        return builder
                .defaultAdvisors(messageMemory,new SimpleLoggerAdvisor(), new SafeGuardAdvisor(List.of("game")))
                .defaultOptions(GoogleGenAiChatOptions
                        .builder()
                        .model("gemini-3.5-flash")
                        .temperature(0.7))
                .build();

    }

    @Bean
    public RestClient restClient() {
        return RestClient
                .builder()
                .baseUrl("http://api.weatherapi.com/v1")
                .build();
    }



}
