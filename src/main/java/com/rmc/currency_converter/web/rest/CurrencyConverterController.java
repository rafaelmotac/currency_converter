package com.rmc.currency_converter.web.rest;

import com.rmc.currency_converter.config.properties.CurrencyConverterProperties;
import com.rmc.currency_converter.dto.ConversionResultDTO;
import com.rmc.currency_converter.dto.CurrentCurrencyDTO;
import com.rmc.currency_converter.dto.ErrorDTO;
import com.rmc.currency_converter.dto.RequestConversionDTO;
import com.rmc.currency_converter.dto.RequestRatesDTO;
import com.rmc.currency_converter.service.CurrencyConverterService;
import com.rmc.currency_converter.util.CurrencyConverterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/api/v1/currency-converter")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CurrencyConverterController {

    private final CurrencyConverterService currencyConverter;
    private final CurrencyConverterProperties currencyConverterProperties;

    @PostMapping(value = "/convert")
    public ResponseEntity<Object> convert(
            @Validated
            @RequestBody
            RequestConversionDTO requestConversionDTO,
            BindingResult bindingResult
    ) {
        log.info("Request to convert: {}", requestConversionDTO);

        ResponseEntity<Object> errorResponse = validateConversion(requestConversionDTO, bindingResult);
        if (errorResponse != null) return errorResponse;

        ConversionResultDTO result =
                this.currencyConverter.convert(
                        requestConversionDTO.getFrom(),
                        requestConversionDTO.getTo(),
                        requestConversionDTO.getAmount());

        return ResponseEntity.ok(result);
    }


    @PostMapping(value = "/current-currency")
    public ResponseEntity<Object> requestRates(
            @Validated
            @RequestBody
            RequestRatesDTO requestRatesDTO,
            BindingResult bindingResult
    ) {

        ResponseEntity<Object> errorResponse = validateRequest(bindingResult);
        if (errorResponse != null) return errorResponse;

        CurrentCurrencyDTO result =
                this.currencyConverter.getCurrentCurrency(
                        requestRatesDTO.getBase(),
                        requestRatesDTO.getSymbols());

        return ResponseEntity.ok(result);

    }

    private ResponseEntity<Object> validateRequest(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            ErrorDTO errorResponse = ErrorDTO.builder()
                    .title("Validation Error")
                    .type("Constraint Violation")
                    .errors(errors)
                    .build();

            return ResponseEntity.badRequest().body(errorResponse);
        }
        return null;
    }

    private ResponseEntity<Object> validateConversion(RequestConversionDTO requestConversionDTO, BindingResult bindingResult) {
        ResponseEntity<Object> errorResponse = validateRequest(bindingResult);
        if (errorResponse != null) return errorResponse;

        CurrencyConverterUtil.validateCurrency(currencyConverterProperties.getCurrencies(), requestConversionDTO.getFrom());
        CurrencyConverterUtil.validateCurrency(currencyConverterProperties.getCurrencies(), requestConversionDTO.getTo());
        return null;
    }
}
