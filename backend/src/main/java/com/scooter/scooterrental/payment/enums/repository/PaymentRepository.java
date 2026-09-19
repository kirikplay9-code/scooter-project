package com.scooter.scooterrental.payment.enums.repository;

import com.scooter.scooterrental.payment.enums.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}