package com.example.project.core.service;

import com.example.project.core.dto.OfficeRateMessage;
import com.example.project.core.entity.Currency;
import com.example.project.core.entity.ExchangeOffice;
import com.example.project.core.entity.Rate;
import com.example.project.core.repositorie.CurrencyRepository;
import com.example.project.core.repositorie.ExchangeOfficeRepository;
import com.example.project.core.repositorie.RateRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RateConsumerService {

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private ExchangeOfficeRepository exchangeOfficeRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @KafkaListener(topics = "office-rates", groupId = "backend-office-rates-group")
    public void consume(ConsumerRecord<String, OfficeRateMessage> record) {
        OfficeRateMessage message = record.value();

        Optional<ExchangeOffice> officeOpt = exchangeOfficeRepository.findByCode(message.officeId());
        Optional<Currency> currencyOpt = currencyRepository.findByCode(message.currency());

        if (officeOpt.isEmpty() || currencyOpt.isEmpty()) {
            System.out.printf("Skipped: office=%s ou currency=%s introuvable en base%n",
                    message.officeId(), message.currency());
            return;
        }

        ExchangeOffice office = officeOpt.get();
        Currency currency = currencyOpt.get();

        Rate rate = rateRepository.findByExchangeOfficeAndCurrency(office, currency)
                .orElseGet(() -> Rate.builder()
                        .exchangeOffice(office)
                        .currency(currency)
                        .build());

        rate.setBuyRate(message.buyRate());
        rate.setSellRate(message.sellRate());

        rateRepository.save(rate);

        System.out.printf("Consumed & saved: [%s] %s buy=%.4f sell=%.4f%n",
                message.officeId(), message.currency(),
                message.buyRate(), message.sellRate());
    }
}