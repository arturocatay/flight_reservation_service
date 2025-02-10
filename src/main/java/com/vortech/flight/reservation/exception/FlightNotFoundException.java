package com.vortech.flight.reservation.exception;

public class FlightNotFoundException extends IllegalStateException {
    public FlightNotFoundException(String message) {
        super(message);
    }

    public FlightNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
