package com.kodilla.controller;

import com.kodilla.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/rates/{table}")
    public Mono<String> getExchangeRates(@PathVariable String table) {
        return exchangeRateService.getExchangeRates(table);
    }

    @GetMapping("/rate/{currencyCode}")
    public Mono<String> getExchangeRateForCurrency(@PathVariable String currencyCode) {
        return exchangeRateService.getExchangeRateForCurrency(currencyCode);
    }
}
