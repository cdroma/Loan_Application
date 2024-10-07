package com.kodilla.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WeatherService {

    private final WebClient webClient;

    private static final String API_KEY = "dbd1fdbbdbd6f5646ebd57dd80a85893";

    public WeatherService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openweathermap.org/data/2.5").build();
    }

    // get 5-day weather forecast for selected city
    public Mono<String> get5DayForecast(String city) {
        return webClient.get()
                .uri("/forecast?q={city}&appid=" + API_KEY + "&units=metric", city)
                .retrieve()
                .bodyToMono(String.class);
    }
}
