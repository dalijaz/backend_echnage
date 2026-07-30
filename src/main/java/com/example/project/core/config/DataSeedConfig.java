package com.example.project.core.config;

import com.example.project.core.entity.Currency;
import com.example.project.core.entity.ExchangeOffice;
import com.example.project.core.repositorie.CurrencyRepository;
import com.example.project.core.repositorie.ExchangeOfficeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeedConfig {

    @Bean
    CommandLineRunner seedData(ExchangeOfficeRepository officeRepo, CurrencyRepository currencyRepo) {
        return args -> {
            seedCurrency(currencyRepo, "TND", "Dinar Tunisien", "د.ت");
            seedCurrency(currencyRepo, "EUR", "Euro", "€");
            seedCurrency(currencyRepo, "USD", "Dollar US", "$");
            seedCurrency(currencyRepo, "GBP", "Livre Sterling", "£");

            seedOffice(officeRepo, "office-tunis", "Bureau Tunis", "Tunis", "Tunisia");
            seedOffice(officeRepo, "office-paris", "Bureau Paris", "Paris", "France");
            seedOffice(officeRepo, "office-london", "Bureau London", "London", "UK");
        };
    }

    private void seedCurrency(CurrencyRepository repo, String code, String name, String symbol) {
        if (repo.findByCode(code).isEmpty()) {
            repo.save(Currency.builder().code(code).name(name).symbol(symbol).build());
        }
    }

    private void seedOffice(ExchangeOfficeRepository repo, String code, String name, String city, String country) {
        if (repo.findByCode(code).isEmpty()) {
            repo.save(ExchangeOffice.builder().code(code).name(name).city(city).country(country).build());
        }
    }
}