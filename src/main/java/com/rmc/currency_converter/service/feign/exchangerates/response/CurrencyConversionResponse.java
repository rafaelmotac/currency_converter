package com.rmc.currency_converter.service.feign.exchangerates.response;

import lombok.Data;

@Data
public class CurrencyConversionResponse {
    private boolean success;
    private Query query;
    private Info info;
    private String historical;
    private String date;
    private Double result;

    @Data
    public static class Query {
        private String from;
        private String to;
        private Double amount;
    }

    @Data
    public static class Info {
        private Long timestamp;
        private Double rate;
    }
}