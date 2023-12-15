package com.rmc.currency_converter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionResultDTO {
    private String from;
    private Double amount;
    private String to;
    private Double result;
}
