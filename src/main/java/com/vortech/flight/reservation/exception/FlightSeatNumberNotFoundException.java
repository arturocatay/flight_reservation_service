package com.vortech.flight.reservation.exception;

public class FlightSeatNumberNotFoundException extends IllegalStateException {
    public FlightSeatNumberNotFoundException(String message) {
        super(message);
    }

    public FlightSeatNumberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}