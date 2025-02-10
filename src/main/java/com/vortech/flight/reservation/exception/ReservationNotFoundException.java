package com.vortech.flight.reservation.exception;

public class ReservationNotFoundException extends IllegalStateException {
    public ReservationNotFoundException(String message) {
        super(message);
    }

    public ReservationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
