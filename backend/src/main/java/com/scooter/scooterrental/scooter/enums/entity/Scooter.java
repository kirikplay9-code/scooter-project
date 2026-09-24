package com.scooter.scooterrental.scooter.enums.entity;

import com.scooter.scooterrental.scooter.enums.ScooterStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "scooters")
@Getter @Setter @NoArgsConstructor
public class Scooter {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String serialNumber;

    private String model;
    private Double latitude;
    private Double longitude;
    private Integer batteryLevel;

    @Enumerated(EnumType.STRING)
    private ScooterStatus status = ScooterStatus.AVAILABLE;
}