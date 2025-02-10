package com.vortech.flight.reservation.dto;

import com.vortech.flight.reservation.model.entity.Seat;
import com.vortech.flight.reservation.template.ResponseTemplate;

import java.io.Serializable;
import java.util.List;

public class SeatResponse extends ResponseTemplate implements Serializable {

    private List<Seat> seatList;

    public SeatResponse() {
        super();
    }

    public SeatResponse(String message, List<Seat> seatList) {
        super(message);
        this.seatList = seatList;
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }

    @Override
    public String toString() {
        return "SeatResponse{" +
                "message='" + message + '\'' +
                ", seatList=" + seatList +
                '}';
    }
}
