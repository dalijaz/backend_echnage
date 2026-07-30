package com.example.auth.DTO.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateFilterDTO {
    private Long id;
    private String currencyCode;
    private String currencyName;
    private String officeName;
    private String country;
    private String city;
    private Double buyRate;
    private Double sellRate;
    private LocalDateTime updatedAt;
}