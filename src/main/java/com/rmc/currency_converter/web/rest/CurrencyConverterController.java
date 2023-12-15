package com.rmc.currency_converter.web.rest;

import com.rmc.currency_converter.config.properties.CurrencyConverterProperties;
import com.rmc.currency_converter.dto.ConversionResultDTO;
import com.rmc.currency_converter.dto.CurrentCurrencyDTO;
import com.rmc.currency_converter.dto.ErrorDTO;
import com.rmc.currency_converter.dto.RequestConversionDTO;
import com.rmc.currency_converter.dto.RequestRatesDTO;
import com.rmc.currency_converter.service.CurrencyConverterService;
import com.rmc.currency_converter.util.CurrencyConverterUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@Tag(name = "Currency Converter", description = "Currency Converter API")
@RequestMapping(value = "/api/v1/currency-converter")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CurrencyConverterController {

    private final CurrencyConverterService currencyConverter;
    private final CurrencyConverterProperties currencyConverterProperties;

    @PostMapping(value = "/convert")
    @Operation(summary = "Convert currency",
            description = "Converts an amount from one currency to another",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(implementation = ConversionResultDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Validation error",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            })
    public ResponseEntity<Object> convert(
            @Validated
            @RequestBody
            @Parameter(description = "Request for currency conversion", required = true,
                    schema = @Schema(implementation = RequestConversionDTO.class))
            RequestConversionDTO requestConversionDTO,
            BindingResult bindingResult
    ) {
        log.info("Request to convert: {}", requestConversionDTO);

        ResponseEntity<Object> errorResponse = validateRequestBindingConstraints(bindingResult);

        if (errorResponse != null) {
            log.error("Validation error during conversion: {}", errorResponse);
            return errorResponse;
        }

        validateCurrency(requestConversionDTO.getFrom(), requestConversionDTO.getTo());

        ConversionResultDTO result =
                this.currencyConverter.convert(
                        requestConversionDTO.getFrom(),
                        requestConversionDTO.getTo(),
                        requestConversionDTO.getAmount());

        log.info("Conversion result: {}", result);

        return ResponseEntity.ok(result);
    }


    @PostMapping(value = "/current-currency")
    @Operation(summary = "Get current rates",
            description = "Get current rates for a given currency",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(implementation = CurrentCurrencyDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Validation error",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            })
    public ResponseEntity<Object> requestRates(
            @Validated
            @RequestBody
            @Parameter(description = "Request for current rates", required = true,
                    schema = @Schema(implementation = RequestRatesDTO.class))
            RequestRatesDTO requestRatesDTO,
            BindingResult bindingResult
    ) {
        log.info("Request to get current rates: {}", requestRatesDTO);

        ResponseEntity<Object> errorResponse = validateRequestBindingConstraints(bindingResult);

        if (errorResponse != null) {
            log.error("Validation error during request rates: {}", errorResponse);
            return errorResponse;
        }

        List<String> currenciesToValidate = new ArrayList<>();
        currenciesToValidate.add(requestRatesDTO.getBase());
        currenciesToValidate.addAll(requestRatesDTO.getSymbols());

        validateCurrency(currenciesToValidate.toArray(new String[0]));

        CurrentCurrencyDTO result =
                this.currencyConverter.getCurrentCurrency(
                        requestRatesDTO.getBase(),
                        requestRatesDTO.getSymbols());

        log.info("Current rates result: {}", result);

        return ResponseEntity.ok(result);

    }


    private ResponseEntity<Object> validateRequestBindingConstraints(BindingResult bindingResult) {
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

    private void validateCurrency(String... currencies) {
        Arrays.stream(currencies).forEach(
                currency -> CurrencyConverterUtil.validateCurrency(currencyConverterProperties.getCurrencies(), currency)
        );
    }
}
