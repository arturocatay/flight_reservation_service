package com.vortech.flight.reservation.dto;

import com.vortech.flight.reservation.model.entity.Flight;
import com.vortech.flight.reservation.template.ResponseTemplate;

import java.io.Serializable;
import java.util.List;

public class FlightResponse extends ResponseTemplate implements Serializable {
    private List<Flight> flightList;

    public FlightResponse() {
        super();
    }

    public FlightResponse(String message, List<Flight> flightList) {
        super(message);
        this.flightList = flightList;
    }

    public List<Flight> getFlightList() {
        return flightList;
    }

    public void setFlightList(List<Flight> flightList) {
        this.flightList = flightList;
    }

    @Override
    public String toString() {
        return "FlightResponse{" +
                "message='" + message + '\'' +
                ", flightList=" + flightList +
                '}';
    }
}
