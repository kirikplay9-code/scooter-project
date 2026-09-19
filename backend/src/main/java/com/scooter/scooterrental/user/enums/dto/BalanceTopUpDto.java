package com.scooter.scooterrental.user.enums.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BalanceTopUpDto {
    @NotNull
    @Positive(message = "Сумма должна быть положительной")
    private Double amount;
}