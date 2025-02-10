package com.vortech.flight.reservation.exception;

public class ReservationAlreadyExistsException extends IllegalStateException {
    public ReservationAlreadyExistsException(String message) {
        super(message);
    }

    public ReservationAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
