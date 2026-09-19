package com.scooter.scooterrental.scooter.enums.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ScooterResponseDto {
    private UUID id;
    private String serialNumber;
    private String model;
    private Double latitude;
    private Double longitude;
    private Integer batteryLevel;
    private String status;
}