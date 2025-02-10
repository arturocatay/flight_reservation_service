package com.vortech.flight.reservation.model.entity;

import com.vortech.flight.reservation.constant.SeatStatus;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    private Flight flight;
    private String seatNumber;
    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    public Seat() {
    }

    public Seat(UUID id, Flight flight, String seatNumber, SeatStatus status) {
        this.id = id;
        this.flight = flight;
        this.seatNumber = seatNumber;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return Objects.equals(id, seat.id) && Objects.equals(flight, seat.flight) && Objects.equals(seatNumber, seat.seatNumber) && status == seat.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flight, seatNumber, status);
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", flight=" + flight +
                ", seatNumber='" + seatNumber + '\'' +
                ", status=" + status +
                '}';
    }
}