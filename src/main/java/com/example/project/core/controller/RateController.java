package com.example.project.core.dto;


import com.example.project.core.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rates")
@CrossOrigin(origins = "http://localhost:4200") // adapte selon ton port Angular

public class RateController {

    @Autowired
    private RateService rateService;

    // GET /api/rates/filter?currencyCode=EUR&country=Tunisia&city=Tunis
    @GetMapping("/filter")
    public ResponseEntity<List<com.example.auth.DTO.dto.RateFilterDTO>> filterRates(
            @RequestParam(required = false) String currencyCode,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city) {
        return ResponseEntity.ok(rateService.filterRates(currencyCode, country, city));
    }

    // GET /api/rates/countries
    @GetMapping("/countries")
    public ResponseEntity<List<String>> getCountries() {
        return ResponseEntity.ok(rateService.getAllCountries());
    }

    // GET /api/rates/cities?country=Tunisia
    @GetMapping("/cities")
    public ResponseEntity<List<String>> getCities(
            @RequestParam(required = false) String country) {
        return ResponseEntity.ok(rateService.getCitiesByCountry(country));
    }

    // GET /api/rates/currencies
    @GetMapping("/currencies")
    public ResponseEntity<List<String>> getCurrencies() {
        return ResponseEntity.ok(rateService.getAllCurrencyCodes());
    }
}