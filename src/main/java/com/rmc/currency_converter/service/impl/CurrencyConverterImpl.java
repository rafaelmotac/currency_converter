package com.rmc.currency_converter.service.impl;

import com.rmc.currency_converter.dto.ConversionResultDTO;
import com.rmc.currency_converter.dto.CurrentCurrencyDTO;
import com.rmc.currency_converter.exception.CurrencyConverterException;
import com.rmc.currency_converter.service.CurrencyConverter;
import com.rmc.currency_converter.service.feign.exchangerates.ExchangeRatesClient;
import com.rmc.currency_converter.service.feign.exchangerates.response.CurrencyConversionResponse;
import com.rmc.currency_converter.service.feign.exchangerates.response.ExchangeRatesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CurrencyConverterImpl implements CurrencyConverter {

    private final ExchangeRatesClient exchangeRatesClient;

    @Override
    public ConversionResultDTO convert(String from, String to, Double amount) {
        CurrencyConversionResponse result = this.exchangeRatesClient.getAmountConverted(from, to, amount);

        if (!result.getSuccess()) {
            log.error("Error converting currency: From {}, To {}, Amount {}.", from, to, amount);
            throw new CurrencyConverterException("Error converting currency");
        }

        return ConversionResultDTO.builder()
                .from(result.getQuery().getFrom())
                .amount(result.getQuery().getAmount())
                .to(result.getQuery().getTo())
                .result(result.getResult())
                .build();

    }

    @Override
    public CurrentCurrencyDTO getCurrentCurrency(String base, List<String> symbols) {
        ExchangeRatesResponse result = this.exchangeRatesClient.getExchangeRates(base, String.join(",", symbols));

        if (!result.isSuccess()) {
            log.error("Error getting current currency: From {}, Symbols {}.", base, symbols);
            throw new CurrencyConverterException("Error getting current currency");
        }

        return CurrentCurrencyDTO.builder()
                .base(result.getBase())
                .rates(result.getRates())
                .build();

    }
}
