package com.scooter.scooterrental.common.exception;

public class ActiveRentalExistsException extends RuntimeException {
    public ActiveRentalExistsException(String message) { super(message); }
}