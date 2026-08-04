package com.kodnest.springaiprointellij.advisors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import reactor.core.publisher.Flux;

public class TokenPrintAdvisor implements CallAdvisor, StreamAdvisor {

    Logger logger = LoggerFactory.getLogger(TokenPrintAdvisor.class);

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {

       logger.info("Model called");
       logger.info("Request: " + chatClientRequest.prompt().getContents());

        ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);

        logger.info("Response: " + chatClientResponse
                .chatResponse()
                .getResult()
                .getOutput()
                .getText());

        logger.info("Prompt Token Consumed: " + chatClientResponse
                .chatResponse()
                .getMetadata()
                .getUsage()
                .getPromptTokens());

        logger.info("Completion Token Consumed: " + chatClientResponse
                .chatResponse()
                .getMetadata()
                .getUsage()
                .getCompletionTokens());


        logger.info("Token Consumed: " + chatClientResponse
                .chatResponse()
                .getMetadata()
                .getUsage()
                .getTotalTokens());

        return chatClientResponse;
    }

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest, StreamAdvisorChain streamAdvisorChain) {
        return streamAdvisorChain.nextStream(chatClientRequest);
    }



    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
