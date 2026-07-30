// ExchangeOfficeRepository.java
package com.example.project.core.repositorie;

import com.example.project.core.entity.ExchangeOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface ExchangeOfficeRepository
        extends JpaRepository<ExchangeOffice, Long> {
    Optional<ExchangeOffice> findByCode(String code);
}