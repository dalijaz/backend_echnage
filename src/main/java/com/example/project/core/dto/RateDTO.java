// RateDTO.java
package com.example.project.core.dto;

import lombok.Data;

@Data
public class RateDTO {
    private Long id;
    private String currencyCode;
    private String currencyName;
    private String currencySymbol;
    private Double buyRate;
    private Double sellRate;
    private String updatedAt;
}