package com.rmc.currency_converter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentCurrencyDTO {

    @Schema(example = "EUR",
            description = "Currency from which the conversion was made.")
    private String base;

    @Schema(example = "{\"USD\": 1.2125, \"GBP\": 0.88955, \"EUR\": 1.0}",
            description = "List containing the currencies requested.")
    private Map<String, Double> rates;

}
