package com.rmc.currency_converter.service.feign.exchangerates;

import com.rmc.currency_converter.service.feign.exchangerates.response.CurrencyConversionResponse;
import com.rmc.currency_converter.service.feign.exchangerates.response.ExchangeRatesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "exchange-rates-api", url = "${currency-converter.exchange-rates-api.url}",
        configuration = ExchangeRatesConfiguration.class)
public interface ExchangeRatesClient {

    @GetMapping("/latest")
    ExchangeRatesResponse getExchangeRates(@RequestParam String base,
                                           @RequestParam String symbols);

    @GetMapping("/convert")
    CurrencyConversionResponse getAmountConverted(@RequestParam String from,
                                                  @RequestParam String to,
                                                  @RequestParam Double amount);

}
