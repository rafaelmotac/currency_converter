package com.rmc.currency_converter.util;

import com.rmc.currency_converter.exception.InvalidCurrencyException;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class CurrencyConverterUtil {

    public static String validateCurrency(List<String> currencies, String currencyToFind) {
        return currencies.stream()
                .filter(currency -> currency.equalsIgnoreCase(currencyToFind))
                .findFirst()
                .orElseThrow(() -> new InvalidCurrencyException("Invalid currency: " + currencyToFind));
    }
}
