package com.vortech.flight.reservation.exception;

public class ReservationFailedException extends IllegalStateException {
    public ReservationFailedException(String message) {
        super(message);
    }

    public ReservationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
