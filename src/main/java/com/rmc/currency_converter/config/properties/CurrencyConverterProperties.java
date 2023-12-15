package com.rmc.currency_converter.config.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "currency-converter")
@NoArgsConstructor
public class CurrencyConverterProperties {

    private ExchangeRatesApiProperties exchangeRatesApi;
    private List<String> currencies;

    @Data
    @NoArgsConstructor
    public static class ExchangeRatesApiProperties {
        private String url;
        private String accessKey;
    }
}

