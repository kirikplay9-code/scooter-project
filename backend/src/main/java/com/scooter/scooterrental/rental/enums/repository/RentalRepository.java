package com.scooter.scooterrental.rental.enums.repository;

import com.scooter.scooterrental.rental.enums.entity.Rental;
import com.scooter.scooterrental.rental.enums.RentalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RentalRepository extends JpaRepository<Rental, UUID> {
    Optional<Rental> findByUserIdAndStatus(UUID userId, RentalStatus status);
    Page<Rental> findByUserIdOrderByStartTimeDesc(UUID userId, Pageable pageable);
}