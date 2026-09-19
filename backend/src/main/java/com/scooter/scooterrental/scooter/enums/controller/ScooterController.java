package com.scooter.scooterrental.scooter.enums.controller;

import com.scooter.scooterrental.scooter.enums.dto.ScooterResponseDto;
import com.scooter.scooterrental.scooter.enums.service.ScooterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/scooters")
@RequiredArgsConstructor
@Tag(name = "Scooter Controller", description = "Просмотр самокатов")
public class ScooterController {
    private final ScooterService scooterService;

    @GetMapping("/available")
    @Operation(summary = "Список доступных самокатов")
    public ResponseEntity<List<ScooterResponseDto>> getAvailable() {
        return ResponseEntity.ok(scooterService.getAvailableScooters());
    }
}