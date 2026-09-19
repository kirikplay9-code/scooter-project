package com.scooter.scooterrental.user.enums.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegistrationDto {
    @NotBlank(message = "логин обязателен")
    private String login;

    @Size(min = 6, message = "Пароль не короче 6 символов")
    private String password;

    @NotBlank(message = "ФИО обязательно")
    private String fullName;

    @NotBlank(message = "Телефон обязателен")
    @Pattern(regexp = "\\+?\\d{10,15}", message = "Неверный формат телефона")
    private String phone;

    @Email(message = "Неверный email")
    @NotBlank(message = "Email обязателен")
    private String email;
}