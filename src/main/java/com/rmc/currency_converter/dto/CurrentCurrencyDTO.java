package com.rmc.currency_converter.dto;

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
    private String base;
    private Map<String, Double> rates;

}
