package com.example.project.core.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "currencies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // USD, EUR, TND...

    @Column(nullable = false)
    private String name; // Dollar, Euro, Dinar...

    @Column(nullable = false)
    private String symbol; // $, €, د.ت
}