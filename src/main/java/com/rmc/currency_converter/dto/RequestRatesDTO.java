package com.rmc.currency_converter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestRatesDTO {

    @Schema(example = "EUR",
            description = "Currency from which the conversion will be made.")
    @NotBlank(message = "The field must contain exactly 3 alphabetic characters.")
    @Pattern(regexp = "^[a-zA-Z]{3}$", message = "The field must contain exactly 3 alphabetic characters.")
    private String base;

    @Schema(example = "[\"USD\", \"GBP\", \"EUR\"]",
            description = "List containing the currencies to request rates.")
    @NotEmpty(message = "The list must contain at least one symbol")
    private List<
            @Pattern(regexp = "^[a-zA-Z]{3}$", message = "Each symbol must contain exactly 3 alphabetic characters.")
                    String>
            symbols;

}
