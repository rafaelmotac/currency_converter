package com.rmc.currency_converter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ErrorDTO {

    private Map<String, String> errors;
    private String type;
    private String title;

}
