package com.example.project.core.service;

import com.example.project.core.dto.*;
import com.example.project.core.entity.*;
import com.example.project.core.repositorie.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeOfficeService {

    private final ExchangeOfficeRepository officeRepository;
    private final RateRepository rateRepository;
    private final CurrencyRepository currencyRepository;

    private static final Map<String, String> COUNTRY_CURRENCY = Map.of(
            "Tunisia", "TND",
            "France", "EUR",
            "USA", "USD",
            "UK", "GBP",
            "Japan", "JPY",
            "Morocco", "MAD",
            "Algeria", "DZD"
    );

    public List<ExchangeOfficeDTO> getAllOffices() {
        return officeRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ExchangeOfficeDTO createOffice(ExchangeOfficeDTO dto) {
        ExchangeOffice office = ExchangeOffice.builder()
                .name(dto.getName())
                .city(dto.getCity())
                .country(dto.getCountry())
                .code(generateUniqueCode(dto.getName()))
                .build();
        return toDTO(officeRepository.save(office));
    }

    // 👇 AJOUT : génère un code unique à partir du nom du bureau
    private String generateUniqueCode(String name) {
        String base = (name == null ? "OFFICE" : name)
                .toUpperCase()
                .replaceAll("[^A-Z0-9]", "");
        if (base.isBlank()) {
            base = "OFFICE";
        }

        String code;
        do {
            String suffix = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
            code = base + "-" + suffix;
        } while (officeRepository.existsByCode(code));

        return code;
    }

    public RateDTO addRate(Long officeId, CreateRateRequest request) {
        ExchangeOffice office = officeRepository.findById(officeId)
                .orElseThrow(() -> new RuntimeException("Bureau introuvable"));

        Currency currency = currencyRepository.findById(request.getCurrencyId())
                .orElseThrow(() -> new RuntimeException("Devise introuvable"));

        // validation 1 : devise locale
        String localCurrency = COUNTRY_CURRENCY.get(office.getCountry());
        if (localCurrency != null && localCurrency.equals(currency.getCode())) {
            throw new RuntimeException(
                    "Impossible d'ajouter " + currency.getCode() +
                            " dans un bureau en " + office.getCountry() +
                            " car c'est la devise locale !"
            );
        }

        // validation 2 : devise déjà existante dans ce bureau
        boolean alreadyExists = rateRepository.findByExchangeOfficeId(officeId)
                .stream()
                .anyMatch(r -> r.getCurrency().getId().equals(request.getCurrencyId()));

        if (alreadyExists) {
            throw new RuntimeException(
                    currency.getCode() + " existe déjà dans ce bureau !"
            );
        }

        Rate rate = Rate.builder()
                .exchangeOffice(office)
                .currency(currency)
                .buyRate(request.getBuyRate())
                .sellRate(request.getSellRate())
                .updatedAt(LocalDateTime.now())
                .build();

        return toRateDTO(rateRepository.save(rate));
    }

    public RateDTO updateRate(Long rateId, UpdateRateRequest request) {
        Rate rate = rateRepository.findById(rateId)
                .orElseThrow(() -> new RuntimeException("Taux introuvable"));
        rate.setBuyRate(request.getBuyRate());
        rate.setSellRate(request.getSellRate());
        rate.setUpdatedAt(LocalDateTime.now());
        return toRateDTO(rateRepository.save(rate));
    }

    public void deleteOffice(Long id) {
        officeRepository.deleteById(id);
    }

    public void deleteRate(Long id) {
        rateRepository.deleteById(id);
    }

    private ExchangeOfficeDTO toDTO(ExchangeOffice office) {
        ExchangeOfficeDTO dto = new ExchangeOfficeDTO();
        dto.setId(office.getId());
        dto.setName(office.getName());
        dto.setCity(office.getCity());
        dto.setCountry(office.getCountry());
        dto.setRates(
                rateRepository.findByExchangeOfficeId(office.getId())
                        .stream()
                        .map(this::toRateDTO)
                        .collect(Collectors.toList())
        );
        return dto;
    }

    private RateDTO toRateDTO(Rate rate) {
        RateDTO dto = new RateDTO();
        dto.setId(rate.getId());
        dto.setCurrencyCode(rate.getCurrency().getCode());
        dto.setCurrencyName(rate.getCurrency().getName());
        dto.setCurrencySymbol(rate.getCurrency().getSymbol());
        dto.setBuyRate(rate.getBuyRate());
        dto.setSellRate(rate.getSellRate());
        dto.setUpdatedAt(rate.getUpdatedAt().toString());
        return dto;
    }

    public List<CurrencyDTO> getAllCurrencies() {
        return currencyRepository.findAll()
                .stream()
                .map(c -> {
                    CurrencyDTO dto = new CurrencyDTO();
                    dto.setId(c.getId());
                    dto.setCode(c.getCode());
                    dto.setName(c.getName());
                    dto.setSymbol(c.getSymbol());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public ExchangeOfficeDTO updateOffice(Long id, ExchangeOfficeDTO dto) {
        ExchangeOffice office = officeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bureau introuvable"));

        office.setName(dto.getName());
        office.setCity(dto.getCity());
        office.setCountry(dto.getCountry());

        return toDTO(officeRepository.save(office));
    }
}