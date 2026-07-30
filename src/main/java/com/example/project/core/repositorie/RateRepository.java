// RateRepository.java
package com.example.project.core.repositorie;
import com.example.project.core.entity.Currency;
import com.example.project.core.entity.ExchangeOffice;
import com.example.project.core.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RateRepository
        extends JpaRepository<Rate, Long> {
    List<Rate> findByExchangeOfficeId(Long officeId);
    Optional<Rate> findByExchangeOfficeAndCurrency(ExchangeOffice exchangeOffice, Currency currency);
}