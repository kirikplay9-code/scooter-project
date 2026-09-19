package com.scooter.scooterrental.rental.enums.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class RentalEndDto {
    @NotNull private UUID rentalId;
    private Double endLatitude;
    private Double endLongitude;
}