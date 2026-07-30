// src/main/java/com/example/project/auth/dto/OfficeRateMessage.java
package com.example.project.core.dto;

import java.time.Instant;

public record OfficeRateMessage(
        String officeId,
        String currency,
        double buyRate,
        double sellRate,
        Instant timestamp
) {}