package com.example.project.core.dto;

import lombok.Data;

@Data
public class UpdateRateRequest {
    private Double buyRate;
    private Double sellRate;
}