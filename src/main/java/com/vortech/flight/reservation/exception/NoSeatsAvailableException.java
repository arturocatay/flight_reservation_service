package com.vortech.flight.reservation.exception;

public class NoSeatsAvailableException extends IllegalArgumentException {
    public NoSeatsAvailableException(String message) {
        super(message);
    }

    public NoSeatsAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
