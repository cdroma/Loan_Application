package com.kodilla.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeRateService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_URL = "https://api.nbp.pl/api/exchangerates/tables/{table}/today/";

    public double getExchangeRate(String  from, String to) {
        String url = API_URL + from;
        ExchangeRateResponse response = restTemplate.getForObject(url, ExchangeRateResponse.class);
        return response.getRates().get(to);
    }
}
