package com.kodilla.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ExchangeRateService {

    private final WebClient webClient;

    public ExchangeRateService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.nbp.pl/api/exchangerates/tables").build();
    }

    public Mono<String> getExchangeRates(String table) {
        return webClient.get()
                .uri("/{table}/today/", table)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getExchangeRateForCurrency(String currencyCode) {
        return webClient.get()
                .uri("/A/{currencyCode}/today/", currencyCode)
                .retrieve()
                .bodyToMono(String.class);
    }
}
