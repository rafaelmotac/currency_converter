package com.rmc.currency_converter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestConversionDTO {

    @Schema(example = "EUR",
            description = "Currency from which the conversion will be made.")
    @NotBlank(message = "You must specify a currency. Ex: EUR")
    @Pattern(regexp = "^[a-zA-Z]{3}$", message = "The field must contain exactly 3 alphabetic characters.")
    private String from;

    @Schema(example = "USD",
            description = "Currency to which the conversion will be made.")
    @NotBlank(message = "You must specify a currency. Ex: EUR")
    @Pattern(regexp = "^[a-zA-Z]{3}$", message = "The field must contain exactly 3 alphabetic characters.")
    private String to;

    @Schema(example = "10.50",
            description = "Amount to be converted.")
    @NotNull(message = "You must specify at least one amount. Ex: 10.50")
    @Min(value = 0, message = "The field must be a positive number.")
    private Double amount;

}
