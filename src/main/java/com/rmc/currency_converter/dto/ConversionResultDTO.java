package com.rmc.currency_converter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionResultDTO {

    @Schema(example = "EUR",
            description = "Currency from which the conversion was made.")
    private String from;

    @Schema(example = "10.50",
            description = "Amount converted.")
    private Double amount;

    @Schema(example = "USD",
            description = "Currency to which the conversion was made.")
    private String to;

    @Schema(example = "11.50",
            description = "Result of the conversion.")
    private Double result;
}
