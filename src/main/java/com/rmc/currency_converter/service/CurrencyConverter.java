package com.rmc.currency_converter.service;

import com.rmc.currency_converter.dto.ConversionResultDTO;
import com.rmc.currency_converter.dto.CurrentCurrencyDTO;

import java.util.List;

public interface CurrencyConverter {

    ConversionResultDTO convert(String from, String to, Double amount);

    CurrentCurrencyDTO getCurrentCurrency(String base, List<String> symbols);

}
