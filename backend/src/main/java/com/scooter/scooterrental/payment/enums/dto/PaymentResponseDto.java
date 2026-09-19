package com.scooter.scooterrental.payment.enums.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PaymentResponseDto {
    private Integer id;
    private Double amount;
    private LocalDateTime paymentDate;
    private String status;
}