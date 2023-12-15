package com.rmc.currency_converter.service.feign.exchangerates.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ExchangeRatesResponse {
    private boolean success;
    private String base;
    private String date;
    private Map<String, Double> rates;
}