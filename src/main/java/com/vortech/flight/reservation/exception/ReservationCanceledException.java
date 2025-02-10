package com.vortech.flight.reservation.exception;

public class ReservationCanceledException extends IllegalStateException {
    public ReservationCanceledException(String message) {
        super(message);
    }

    public ReservationCanceledException(String message, Throwable cause) {
        super(message, cause);
    }
}
