package com.scooter.scooterrental.rental.enums.service;

import com.scooter.scooterrental.common.exception.*;
import com.scooter.scooterrental.payment.enums.entity.Payment;
import com.scooter.scooterrental.payment.enums.PaymentStatus;
import com.scooter.scooterrental.payment.enums.repository.PaymentRepository;
import com.scooter.scooterrental.rental.enums.dto.*;
import com.scooter.scooterrental.rental.enums.entity.Rental;
import com.scooter.scooterrental.rental.enums.RentalStatus;
import com.scooter.scooterrental.rental.enums.repository.RentalRepository;
import com.scooter.scooterrental.scooter.enums.entity.Scooter;
import com.scooter.scooterrental.scooter.enums.ScooterStatus;
import com.scooter.scooterrental.scooter.enums.service.ScooterService;
import com.scooter.scooterrental.tariff.entity.Tariff;
import com.scooter.scooterrental.tariff.entity.service.TariffService;
import com.scooter.scooterrental.user.enums.entity.User;
import com.scooter.scooterrental.user.enums.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final UserService userService;
    private final ScooterService scooterService;
    private final TariffService tariffService;
    private final PaymentRepository paymentRepository;

    @Transactional
    public RentalResponseDto startRental(RentalStartDto dto) {
        User user = userService.getUserById(dto.getUserId());
        Scooter scooter = scooterService.getScooterById(dto.getScooterId());


        if (scooter.getStatus() != ScooterStatus.AVAILABLE) {
            throw new ScooterNotAvailableException("Самокат не доступен");
        }
        if (rentalRepository.findByUserIdAndStatus(user.getId(), RentalStatus.ACTIVE).isPresent()) {
            throw new ActiveRentalExistsException("У пользователя уже есть активная аренда");
        }

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setScooter(scooter);
        rental.setStartTime(LocalDateTime.now());
        rental.setStartLatitude(dto.getStartLatitude());
        rental.setStartLongitude(dto.getStartLongitude());
        rental.setStatus(RentalStatus.ACTIVE);

        rentalRepository.save(rental);

        scooterService.updateScooterStatus(scooter.getId(), ScooterStatus.RENTED);

        return toResponseDto(rental);
    }

    @Transactional
    public RentalResponseDto endRental(RentalEndDto dto) {
        Rental rental = rentalRepository.findById(dto.getRentalId())
                .orElseThrow(() -> new RentalNotFoundException("Аренда не найдена"));
        if (rental.getStatus() != RentalStatus.ACTIVE) {
            throw new IllegalStateException("Аренда не активирована");
        }
        LocalDateTime endTime = LocalDateTime.now();
        rental.setEndTime(endTime);
        rental.setEndLatitude(dto.getEndLatitude());
        rental.setEndLongitude(dto.getEndLongitude());
        Tariff tariff = tariffService.getDefaultTariff();
        long minutes = Duration.between(rental.getStartTime(), endTime).toMinutes();
        if (minutes < 1) minutes = 1;
        double cost = tariff.getStartPrice() + minutes * tariff.getPricePerMinute();
        rental.setTotalCost(cost);
        User user = rental.getUser();
        if (user.getBalance() < cost) {
            throw new InsufficientBalanceException("недостаточно средств. Необходимо " + cost);
        }
        user.setBalance(user.getBalance() - cost);
        userService.updateUser(user);
        Payment payment = new Payment();
        payment.setRental(rental);
        payment.setAmount(cost);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus(PaymentStatus.PAID);
        paymentRepository.save(payment);
        rental.setStatus(RentalStatus.FINISHED);
        rentalRepository.save(rental);
        scooterService.updateScooterStatus(rental.getScooter().getId(), ScooterStatus.AVAILABLE);
        return toResponseDto(rental);
    }

    @Transactional(readOnly = true)
    public RentalResponseDto getActiveRental(UUID userId) {
        Rental rental = rentalRepository.findByUserIdAndStatus(userId, RentalStatus.ACTIVE)
                .orElseThrow(() -> new RentalNotFoundException("Нет активной аренды"));
        return toResponseDto(rental);
    }

    @Transactional(readOnly = true)
    public Page<RentalResponseDto> getRentalHistory(UUID userId, Pageable pageable) {
        return rentalRepository.findByUserIdOrderByStartTimeDesc(userId, pageable)
                .map(this::toResponseDto);
    }

    private RentalResponseDto toResponseDto(Rental rental) {
        RentalResponseDto dto = new RentalResponseDto();
        dto.setRentalId(rental.getId());
        dto.setScooterSerial(rental.getScooter().getSerialNumber());
        dto.setStartTime(rental.getStartTime());
        dto.setEndTime(rental.getEndTime());
        dto.setTotalCost(rental.getTotalCost());
        dto.setStatus(rental.getStatus().name());
        return dto;
    }
}