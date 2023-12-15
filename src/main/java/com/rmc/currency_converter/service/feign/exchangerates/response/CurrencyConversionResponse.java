package com.rmc.currency_converter.service.feign.exchangerates.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyConversionResponse {
    private boolean success;
    private Query query;
    private String date;
    private Double result;

    @Getter
    @Setter
    public static class Query {
        private String from;
        private String to;
        private Double amount;
    }

}