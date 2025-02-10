package com.vortech.flight.reservation.exception;

public class SendEmailReservationException extends Exception {
    public SendEmailReservationException(String message) {
        super(message);
    }

    public SendEmailReservationException(String message, Throwable cause) {
        super(message, cause);
    }
}
