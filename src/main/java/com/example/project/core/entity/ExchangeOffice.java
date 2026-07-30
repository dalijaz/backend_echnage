package com.example.project.core.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "exchange_offices")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Bureau A, Bureau B...
    @Column(nullable = false, unique = true)
    private String code;
    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @OneToMany(mappedBy = "exchangeOffice", cascade = CascadeType.ALL)
    private List<Rate> rates;
}