package com.kodnest.springaiprointellij.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class WeatherTool {

    public RestClient restClient;

    public WeatherTool(RestClient restClient) {
        this.restClient = restClient;
    }

    @Value("${spring.ai.weather-api}")
    private String weatherApi;

    @Tool(description = "Get weather of given city.")
    public String getWeather(@ToolParam(description="city of weather we want to get weather information")  String city) {

        Map<String, Object> response = restClient.
                get()
                .uri(builder -> builder.path("/current.json")
                        .queryParam("key", weatherApi)
                        .queryParam("q", city)
                        .build()
                )
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String, Object>>() {
                });

       return response.toString();
    }

}
