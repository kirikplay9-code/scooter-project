package com.scooter.scooterrental.user.enums.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UserResponseDto {
    private UUID id;
    private String login;
    private String fullName;
    private String email;
    private Double balance;
}