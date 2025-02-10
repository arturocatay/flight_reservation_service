package com.vortech.flight.reservation.dto;

import com.vortech.flight.reservation.model.entity.Reservation;
import com.vortech.flight.reservation.template.ResponseTemplate;

import java.io.Serializable;
import java.util.List;

public class ReservationResponse extends ResponseTemplate implements Serializable {


    private List<Reservation> reservationList;

    public ReservationResponse() {
        super();
    }

    public ReservationResponse(String message, List<Reservation> reservationList) {
        super(message);
        this.reservationList = reservationList;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    @Override
    public String toString() {
        return "ReservationResponse{" +
                "message='" + message + '\'' +
                ", reservationList=" + reservationList +
                '}';
    }
}
