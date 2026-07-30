package com.example.project.core.controller;

import com.example.project.core.dto.*;
import com.example.project.core.entity.Currency;
import com.example.project.core.repositorie.CurrencyRepository;
import com.example.project.core.service.ExchangeOfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ExchangeOfficeService officeService;
    private final CurrencyRepository currencyRepository;

    @GetMapping("/offices")
    public ResponseEntity<List<ExchangeOfficeDTO>> getAllOffices() {
        return ResponseEntity.ok(officeService.getAllOffices());
    }

    @GetMapping("/currencies")
    public ResponseEntity<List<Currency>> getAllCurrencies() {
        return ResponseEntity.ok(currencyRepository.findAll());
    }

    @PostMapping("/offices")
    public ResponseEntity<ExchangeOfficeDTO> createOffice(
            @RequestBody ExchangeOfficeDTO dto) {
        return ResponseEntity.ok(officeService.createOffice(dto));
    }

    @PostMapping("/offices/{id}/rates")
    public ResponseEntity<RateDTO> addRate(
            @PathVariable Long id,
            @RequestBody CreateRateRequest request) {
        return ResponseEntity.ok(officeService.addRate(id, request));
    }

    @PostMapping("/offices/{id}/currencies")
    public ResponseEntity<RateDTO> addCurrency(
            @PathVariable Long id,
            @RequestBody CreateRateRequest request) {
        return ResponseEntity.ok(officeService.addRate(id, request));
    }

    @PutMapping("/rates/{id}")
    public ResponseEntity<RateDTO> updateRate(
            @PathVariable Long id,
            @RequestBody UpdateRateRequest request) {
        return ResponseEntity.ok(officeService.updateRate(id, request));
    }

    @DeleteMapping("/offices/{id}")
    public ResponseEntity<Void> deleteOffice(@PathVariable Long id) {
        officeService.deleteOffice(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/rates/{id}")
    public ResponseEntity<Void> deleteRate(@PathVariable Long id) {
        officeService.deleteRate(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/offices/{id}")
    public ResponseEntity<ExchangeOfficeDTO> updateOffice(
            @PathVariable Long id,
            @RequestBody ExchangeOfficeDTO dto) {
        return ResponseEntity.ok(officeService.updateOffice(id, dto));
    }

    // 👇 AJOUT : capte les RuntimeException levées par ExchangeOfficeService
    // (devise locale, doublon...) et renvoie un JSON propre avec le code 400
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", e.getMessage()));
    }
}