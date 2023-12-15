package com.rmc.currency_converter.service.feign.exchangerates.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ExchangeRatesResponse {
    private boolean success;
    private long timestamp;
    private String base;
    private String date;
    private Map<String, Double> rates;
}