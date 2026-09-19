package com.scooter.scooterrental.common.exception;

public class RentalNotFoundException extends RuntimeException {
    public RentalNotFoundException(String message) { super(message); }
}