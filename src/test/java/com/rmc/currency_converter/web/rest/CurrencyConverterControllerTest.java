package com.rmc.currency_converter.web.rest;

import com.rmc.currency_converter.config.properties.CurrencyConverterProperties;
import com.rmc.currency_converter.dto.RequestConversionDTO;
import com.rmc.currency_converter.dto.RequestRatesDTO;
import com.rmc.currency_converter.exception.InvalidCurrencyException;
import com.rmc.currency_converter.service.CurrencyConverterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CurrencyConverterControllerTest {

    @Mock
    private CurrencyConverterService currencyConverterService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private CurrencyConverterProperties configurationProperties;

    @InjectMocks
    private CurrencyConverterController currencyConverterController;

    @Test
    void shouldConvertCurrencySuccessfully() {
        RequestConversionDTO request = new RequestConversionDTO();
        request.setFrom("USD");
        request.setTo("EUR");
        request.setAmount(100.0);
        when(currencyConverterService.convert(anyString(), anyString(), anyDouble())).thenReturn(null);
        when(configurationProperties.getCurrencies()).thenReturn(List.of("USD", "EUR"));

        ResponseEntity<Object> response = currencyConverterController.convert(request, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldReturnBadRequestWhenConversionValidationFails() {
        RequestConversionDTO request = new RequestConversionDTO();
        request.setFrom("USD");
        request.setTo("EUR");
        request.setAmount(100.0);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(configurationProperties.getCurrencies()).thenReturn(List.of("USD", "EUR"));

        ResponseEntity<Object> response = currencyConverterController.convert(request, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldReturnBadRequestWhenInvalidCurrency() {
        RequestConversionDTO request = new RequestConversionDTO();
        request.setFrom("XXX");
        request.setTo("EUR");
        request.setAmount(100.0);
        when(configurationProperties.getCurrencies()).thenReturn(List.of("EUR"));

        assertThrows(InvalidCurrencyException.class,
                () -> currencyConverterController.convert(request, bindingResult)
        );
    }

    @Test
    void shouldGetCurrentCurrencySuccessfully() {
        RequestRatesDTO request = new RequestRatesDTO();
        request.setBase("USD");
        request.setSymbols(List.of("EUR"));
        when(configurationProperties.getCurrencies()).thenReturn(List.of("USD", "EUR"));
        when(currencyConverterService.getCurrentCurrency(anyString(), anyList())).thenReturn(null);

        ResponseEntity<Object> response = currencyConverterController.requestRates(request, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldReturnBadRequestWhenCurrentCurrencyValidationFails() {
        RequestRatesDTO request = new RequestRatesDTO();
        request.setBase("USD");
        request.setSymbols(Collections.singletonList("EUR"));
        when(bindingResult.hasErrors()).thenReturn(true);

        FieldError fieldError = new FieldError("request", "base", "Invalid base currency");

        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        when(configurationProperties.getCurrencies()).thenReturn(List.of("USD", "EUR"));

        ResponseEntity<Object> response = currencyConverterController.requestRates(request, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}