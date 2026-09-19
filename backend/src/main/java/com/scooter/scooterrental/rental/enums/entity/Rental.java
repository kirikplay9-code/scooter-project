package com.scooter.scooterrental.rental.enums.entity;

import com.scooter.scooterrental.rental.enums.RentalStatus;
import com.scooter.scooterrental.user.enums.entity.User;
import com.scooter.scooterrental.scooter.enums.entity.Scooter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "rentals")
@Getter @Setter @NoArgsConstructor
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scooter_id", nullable = false)
    private Scooter scooter;

    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Double startLatitude;
    private Double startLongitude;
    private Double endLatitude;
    private Double endLongitude;

    private Double totalCost;

    @Enumerated(EnumType.STRING)
    private RentalStatus status = RentalStatus.ACTIVE;
}