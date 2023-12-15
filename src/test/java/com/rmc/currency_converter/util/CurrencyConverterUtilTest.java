package com.rmc.currency_converter.util;

import com.rmc.currency_converter.exception.InvalidCurrencyException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.rmc.currency_converter.util.CurrencyConverterUtil.validateCurrency;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CurrencyConverterUtilTest {

    @Test
    void shouldValidateCurrencySuccessfully() {
        List<String> currencies = Arrays.asList("USD", "EUR", "GBP");
        String currencyToFind = "USD";

        String result = validateCurrency(currencies, currencyToFind);

        assertEquals(currencyToFind, result);
    }

    @Test
    void shouldThrowExceptionWhenCurrencyIsInvalid() {
        List<String> currencies = Arrays.asList("USD", "EUR", "GBP");
        String currencyToFind = "ABC";

        assertThrows(InvalidCurrencyException.class, () -> validateCurrency(currencies, currencyToFind));
    }
}
