package com.rmc.currency_converter.service.impl;

import com.rmc.currency_converter.dto.ConversionResultDTO;
import com.rmc.currency_converter.dto.CurrentCurrencyDTO;
import com.rmc.currency_converter.exception.CurrencyConverterException;
import com.rmc.currency_converter.service.feign.exchangerates.ExchangeRatesClient;
import com.rmc.currency_converter.service.feign.exchangerates.response.CurrencyConversionResponse;
import com.rmc.currency_converter.service.feign.exchangerates.response.ExchangeRatesResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CurrencyConverterServiceImplTest {

    @Mock
    private ExchangeRatesClient exchangeRatesClient;

    @InjectMocks
    private CurrencyConverterServiceImpl currencyConverterService;

    @Test
    void shouldConvertCurrencySuccessfully() {
        CurrencyConversionResponse mockResponse = new CurrencyConversionResponse();
        mockResponse.setSuccess(true);
        mockResponse.setResult(100.0);
        CurrencyConversionResponse.Query query = new CurrencyConversionResponse.Query();
        query.setFrom("USD");
        query.setTo("EUR");
        query.setAmount(100.0);
        mockResponse.setQuery(query);

        when(exchangeRatesClient.getAmountConverted("USD", "EUR", 100.0)).thenReturn(mockResponse);

        ConversionResultDTO result = currencyConverterService.convert("USD", "EUR", 100.0);

        assertEquals("USD", result.getFrom());
        assertEquals("EUR", result.getTo());
        assertEquals(100.0, result.getAmount());
        assertEquals(100.0, result.getResult());
    }

    @Test
    void shouldThrowExceptionWhenConversionFails() {
        CurrencyConversionResponse mockResponse = new CurrencyConversionResponse();
        mockResponse.setSuccess(false);
        when(exchangeRatesClient.getAmountConverted("USD", "EUR", 100.0)).thenReturn(mockResponse);

        assertThrows(CurrencyConverterException.class,
                () -> currencyConverterService.convert("USD", "EUR", 100.0)
        );
    }

    @Test
    void shouldGetCurrentCurrencySuccessfully() {
        ExchangeRatesResponse mockResponse = new ExchangeRatesResponse();
        mockResponse.setSuccess(true);
        Map<String, Double> rates = new HashMap<>();
        rates.put("EUR", 0.85);
        rates.put("GBP", 0.75);
        mockResponse.setRates(rates);
        mockResponse.setBase("USD");
        when(exchangeRatesClient.getExchangeRates("USD", "EUR,GBP")).thenReturn(mockResponse);

        CurrentCurrencyDTO result = currencyConverterService.getCurrentCurrency("USD", Arrays.asList("EUR", "GBP"));

        assertEquals("USD", result.getBase());
        assertEquals(rates, result.getRates());
    }

    @Test
    void shouldThrowExceptionWhenGettingCurrentCurrencyFails() {
        ExchangeRatesResponse mockResponse = new ExchangeRatesResponse();
        mockResponse.setSuccess(false);
        when(exchangeRatesClient.getExchangeRates("USD", "EUR,GBP")).thenReturn(mockResponse);

        List<String> currencyList = List.of("EUR", "GBP");

        assertThrows(CurrencyConverterException.class,
                () -> currencyConverterService.getCurrentCurrency("USD", currencyList)
        );
    }
}