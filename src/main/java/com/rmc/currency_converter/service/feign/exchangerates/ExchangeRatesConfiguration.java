package com.rmc.currency_converter.service.feign.exchangerates;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class ExchangeRatesConfiguration {

    private final String accessKey;

    public ExchangeRatesConfiguration(
            @Value("${currency-converter.exchange-rates-api.access-key}") String accessKey) {
        this.accessKey = accessKey;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate ->
                requestTemplate.query("access_key", accessKey);
    }

}