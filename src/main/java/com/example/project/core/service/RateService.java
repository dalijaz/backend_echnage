package com.example.project.core.service;
import com.example.project.core.entity.Rate;
import com.example.project.core.repositorie.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.auth.DTO.dto.RateFilterDTO;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateService {

    @Autowired
    private RateRepository rateRepository;

    // ---------- FILTRAGE PRINCIPAL ----------
    public List<RateFilterDTO> filterRates(String currencyCode, String country, String city) {
        return rateRepository.findAll()
                .stream()
                .filter(rate -> matchCurrency(rate, currencyCode))
                .filter(rate -> matchCountry(rate, country))
                .filter(rate -> matchCity(rate, city))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ---------- LISTES POUR LES DROPDOWNS ----------

    public List<String> getAllCountries() {
        return rateRepository.findAll()
                .stream()
                .map(rate -> rate.getExchangeOffice().getCountry())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<String> getCitiesByCountry(String country) {
        return rateRepository.findAll()
                .stream()
                .filter(rate -> matchCountry(rate, country))
                .map(rate -> rate.getExchangeOffice().getCity())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<String> getAllCurrencyCodes() {
        return rateRepository.findAll()
                .stream()
                .map(rate -> rate.getCurrency().getCode())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    // ---------- HELPERS PRIVÉS ----------

    private boolean matchCurrency(Rate rate, String currencyCode) {
        return isEmpty(currencyCode)
                || rate.getCurrency().getCode().equalsIgnoreCase(currencyCode);
    }

    private boolean matchCountry(Rate rate, String country) {
        return isEmpty(country)
                || rate.getExchangeOffice().getCountry().equalsIgnoreCase(country);
    }

    private boolean matchCity(Rate rate, String city) {
        return isEmpty(city)
                || rate.getExchangeOffice().getCity().equalsIgnoreCase(city);
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private RateFilterDTO toDTO(Rate rate) {
        return new RateFilterDTO(
                rate.getId(),
                rate.getCurrency().getCode(),
                rate.getCurrency().getName(),
                rate.getExchangeOffice().getName(),
                rate.getExchangeOffice().getCountry(),
                rate.getExchangeOffice().getCity(),
                rate.getBuyRate(),
                rate.getSellRate(),
                rate.getUpdatedAt()
        );
    }
}