package com.scooter.scooterrental.user.enums.controller;

import com.scooter.scooterrental.user.enums.dto.*;
import com.scooter.scooterrental.user.enums.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Регистрация, вход, баланс")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Регистрация нового клиента")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRegistrationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(dto));
    }

    @PostMapping("/login")
    @Operation(summary = "Вход в систему")
    public ResponseEntity<UserResponseDto> login(@Valid @RequestBody UserLoginDto dto) {
        return ResponseEntity.ok(userService.login(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить информацию о пользователе")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserInfo(id));
    }

    @PostMapping("/{id}/topup")
    @Operation(summary = "Пополнить баланс")
    public ResponseEntity<Void> topUpBalance(@PathVariable UUID id, @Valid @RequestBody BalanceTopUpDto dto) {
        userService.topUpBalance(id, dto.getAmount());
        return ResponseEntity.noContent().build();
    }
}