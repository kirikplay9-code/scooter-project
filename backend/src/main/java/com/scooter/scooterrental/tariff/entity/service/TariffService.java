package com.scooter.scooterrental.tariff.entity.service;

import com.scooter.scooterrental.tariff.entity.Tariff;
import com.scooter.scooterrental.tariff.entity.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TariffService {
    private final TariffRepository tariffRepository;

    public Tariff getDefaultTariff() {
        return tariffRepository.findById(1).orElseThrow(() -> new RuntimeException("нет тарифа"));

    }
}