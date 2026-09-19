package com.scooter.scooterrental.scooter.enums.repository;

import com.scooter.scooterrental.scooter.enums.entity.Scooter;
import com.scooter.scooterrental.scooter.enums.ScooterStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ScooterRepository extends JpaRepository<Scooter, UUID> {
    List<Scooter> findByStatus(ScooterStatus status);
    Optional<Scooter> findByIdAndStatus(UUID id, ScooterStatus status);
}