package com.vortech.flight.reservation.dto;

import com.vortech.flight.reservation.template.ResponseTemplate;

import java.io.Serializable;

public class CanceledReservationResponse extends ResponseTemplate implements Serializable {

    public CanceledReservationResponse() {
    }

    public CanceledReservationResponse(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "CanceledReservationResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
