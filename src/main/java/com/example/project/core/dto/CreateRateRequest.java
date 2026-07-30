// CreateRateRequest.java
package com.example.project.core.dto;

import lombok.Data;

@Data
public class CreateRateRequest {
    private Long currencyId;
    private Double buyRate;
    private Double sellRate;
}