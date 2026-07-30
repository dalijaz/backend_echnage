package com.example.project.core.repositorie;

import com.example.project.core.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CurrencyRepository
        extends JpaRepository<Currency, Long> {

    boolean existsByCode(String code);
    Optional<Currency> findByCode(String code);
}