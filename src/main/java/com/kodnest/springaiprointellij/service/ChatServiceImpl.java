package com.kodnest.springaiprointellij.service;

import com.kodnest.springaiprointellij.tools.SimpleDateTimeTool;
import com.kodnest.springaiprointellij.tools.WeatherTool;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ChatClient chatClient;
    private  VectorStore vectorStore;

    @Value("classpath:prompt/userPrompt.st")
    private Resource userPrompt;

    @Value("classpath:prompt/systemPrompt.st")
    private Resource systemPrompt;

    private WeatherTool weatherTool;

    public ChatServiceImpl(ChatClient chatClient, VectorStore vectorStore, WeatherTool weatherTool)
    {
        super();
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
        this.weatherTool = weatherTool;
    }

    public String chat(String q, String userId) {

//        SearchRequest search = SearchRequest.builder()
//                .topK(5)
//                .similarityThreshold(0.6)
//                .query(q)
//                .build();
//
//        // Load data from the Vector Database
//        List<Document> documents = this.vectorStore.similaritySearch(search);
//        List<@Nullable String> list = documents.stream().map(Document::getText).toList();
//        String contextData = String.join(" , ", list);
//        logger.info("contextData: {}", contextData);
        // similar result from the user




//        String queryString = "You give five {topic} Concepts";
       return  chatClient.prompt()
               .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, userId))
               .advisors(QuestionAnswerAdvisor.builder(vectorStore).build())
//                .system(system -> system.text(systemPrompt).param("documents", contextData))
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

    @Override
    public void savedData(List<String> list) {

        List<Document> collect = list.stream().map(Document::new).toList();

        this.vectorStore.add(collect);
    }

    @Override
    public String chatQ(String q, String conversationId) {
        return this.chatClient
                .prompt()
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId))
                .tools(new SimpleDateTimeTool(), weatherTool)
                .user(q)
                .call()
                .content();
    }

}
