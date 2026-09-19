package com.scooter.scooterrental.rental.enums.controller;

import com.scooter.scooterrental.rental.enums.dto.*;
import com.scooter.scooterrental.rental.enums.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
@Tag(name = "Rental Controller", description = "Управление арендой")
public class RentalController {
    private final RentalService rentalService;

    @PostMapping("/start")
    @Operation(summary = "Начать аренду")
    public ResponseEntity<RentalResponseDto> startRental(@Valid @RequestBody RentalStartDto dto) {
        return ResponseEntity.ok(rentalService.startRental(dto));
    }

    @PostMapping("/end")
    @Operation(summary = "Завершить аренду")
    public ResponseEntity<RentalResponseDto> endRental(@Valid @RequestBody RentalEndDto dto) {
        return ResponseEntity.ok(rentalService.endRental(dto));
    }

    @GetMapping("/active/{userId}")
    @Operation(summary = "Активная аренда пользователя")
    public ResponseEntity<RentalResponseDto> getActive(@PathVariable UUID userId) {
        return ResponseEntity.ok(rentalService.getActiveRental(userId));
    }

    @GetMapping("/history/{userId}")
    @Operation(summary = "История поездок")
    public ResponseEntity<Page<RentalResponseDto>> getHistory(@PathVariable UUID userId,
                                                              @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(rentalService.getRentalHistory(userId, pageable));
    }
}