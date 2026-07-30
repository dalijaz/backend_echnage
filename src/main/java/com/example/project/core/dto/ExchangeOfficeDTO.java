// ExchangeOfficeDTO.java
package com.example.project.core.dto;

import lombok.Data;
import java.util.List;

@Data
public class ExchangeOfficeDTO {
    private Long id;
    private String name;
    private String city;
    private String country;
    private List<RateDTO> rates;

}