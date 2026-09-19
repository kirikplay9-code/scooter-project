package com.scooter.scooterrental.scooter.enums.service;

import com.scooter.scooterrental.common.exception.ScooterNotFoundException;
import com.scooter.scooterrental.scooter.enums.dto.ScooterResponseDto;
import com.scooter.scooterrental.scooter.enums.entity.Scooter;
import com.scooter.scooterrental.scooter.enums.ScooterStatus;
import com.scooter.scooterrental.scooter.enums.repository.ScooterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScooterService {
    private final ScooterRepository scooterRepository;

    @Transactional(readOnly = true)
    public List<ScooterResponseDto> getAvailableScooters() {
        return scooterRepository.findByStatus(ScooterStatus.AVAILABLE)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Scooter getScooterById(UUID id) {
        return scooterRepository.findById(id)
                .orElseThrow(() -> new ScooterNotFoundException("Самокат не найден"));
    }

    @Transactional
    public void updateScooterStatus(UUID id, ScooterStatus status) {
        Scooter scooter = getScooterById(id);
        scooter.setStatus(status);
        scooterRepository.save(scooter);
    }

    private ScooterResponseDto toDto(Scooter scooter) {
        ScooterResponseDto dto = new ScooterResponseDto();
        dto.setId(scooter.getId());
        dto.setSerialNumber(scooter.getSerialNumber());
        dto.setModel(scooter.getModel());
        dto.setLatitude(scooter.getLatitude());
        dto.setLongitude(scooter.getLongitude());
        dto.setBatteryLevel(scooter.getBatteryLevel());
        dto.setStatus(scooter.getStatus().name());
        return dto;
    }
}