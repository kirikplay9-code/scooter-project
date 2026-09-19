package com.scooter.scooterrental.user.enums.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDto {
    @NotBlank private String login;
    @NotBlank private String password;
}