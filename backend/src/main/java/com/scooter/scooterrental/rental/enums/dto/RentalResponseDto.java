package com.scooter.scooterrental.rental.enums.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RentalResponseDto {
    private UUID rentalId;
    private String scooterSerial;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double totalCost;
    private String status;
}