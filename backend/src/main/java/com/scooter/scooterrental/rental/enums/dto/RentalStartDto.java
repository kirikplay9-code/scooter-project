package com.scooter.scooterrental.rental.enums.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class RentalStartDto {
    @NotNull private UUID userId;
    @NotNull private UUID scooterId;
    private Double startLatitude;
    private Double startLongitude;
}