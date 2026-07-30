package com.example.project.core.config;

import com.example.project.core.entity.Currency;
import com.example.project.core.entity.Role;
import com.example.project.core.entity.User;
import com.example.project.core.repositorie.CurrencyRepository;
import com.example.project.core.repositorie.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CurrencyRepository currencyRepository;

    @Override
    public void run(String... args) {

        // Créer ou corriger l'admin
        User admin = userRepository.findByEmail("admin@forex.com").orElse(null);
        if (admin == null) {
            admin = User.builder()
                    .username("admin")
                    .email("admin@forex.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
            System.out.println(">>> Admin créé !");
        } else if (admin.getRole() != Role.ADMIN) {
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
            System.out.println(">>> Rôle admin corrigé !");
        }

        // Toutes les devises à avoir en base
        List<Currency> allCurrencies = List.of(
                // Devises majeures
                Currency.builder().code("USD").name("Dollar américain").symbol("$").build(),
                Currency.builder().code("EUR").name("Euro").symbol("€").build(),
                Currency.builder().code("GBP").name("Livre sterling").symbol("£").build(),
                Currency.builder().code("JPY").name("Yen japonais").symbol("¥").build(),
                Currency.builder().code("CHF").name("Franc suisse").symbol("CHF").build(),
                Currency.builder().code("CAD").name("Dollar canadien").symbol("CA$").build(),
                Currency.builder().code("AUD").name("Dollar australien").symbol("A$").build(),

                // Devises arabes et africaines
                Currency.builder().code("TND").name("Dinar tunisien").symbol("DT").build(),
                Currency.builder().code("MAD").name("Dirham marocain").symbol("MAD").build(),
                Currency.builder().code("DZD").name("Dinar algérien").symbol("DA").build(),
                Currency.builder().code("EGP").name("Livre égyptienne").symbol("E£").build(),
                Currency.builder().code("LYD").name("Dinar libyen").symbol("LD").build(),
                Currency.builder().code("SAR").name("Riyal saoudien").symbol("SR").build(),
                Currency.builder().code("AED").name("Dirham des EAU").symbol("AED").build(),
                Currency.builder().code("QAR").name("Riyal qatari").symbol("QR").build(),
                Currency.builder().code("KWD").name("Dinar koweïtien").symbol("KD").build(),

                // Autres devises
                Currency.builder().code("CNY").name("Yuan chinois").symbol("¥").build(),
                Currency.builder().code("INR").name("Roupie indienne").symbol("₹").build(),
                Currency.builder().code("TRY").name("Livre turque").symbol("₺").build(),
                Currency.builder().code("RUB").name("Rouble russe").symbol("₽").build(),
                Currency.builder().code("BRL").name("Réal brésilien").symbol("R$").build()
        );

        // Récupérer les codes déjà existants en base
        List<String> existingCodes = currencyRepository.findAll()
                .stream()
                .map(Currency::getCode)
                .collect(Collectors.toList());

        // Ajouter seulement les devises manquantes
        List<Currency> toAdd = allCurrencies.stream()
                .filter(c -> !existingCodes.contains(c.getCode()))
                .collect(Collectors.toList());

        if (!toAdd.isEmpty()) {
            currencyRepository.saveAll(toAdd);
            System.out.println(">>> " + toAdd.size() + " devises ajoutées !");
        } else {
            System.out.println(">>> Toutes les devises sont déjà en base !");
        }
    }
}