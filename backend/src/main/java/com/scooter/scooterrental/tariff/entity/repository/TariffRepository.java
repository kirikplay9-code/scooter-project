package com.scooter.scooterrental.tariff.entity.repository;

import com.scooter.scooterrental.tariff.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Integer> {
}