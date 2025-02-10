package com.vortech.flight.reservation.model.entity;

import com.vortech.flight.reservation.constant.ReservationStatus;
import com.vortech.flight.reservation.util.HexGenerator;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    private Flight flight;
    @ManyToOne
    private Seat seat;
    private String userEmail;
    private String reservationNumber;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public Reservation() {
    }

    public Reservation(UUID id, Flight flight, Seat seat, String userEmail, String reservationNumber, ReservationStatus status) {
        this.id = id;
        this.flight = flight;
        this.seat = seat;
        this.userEmail = userEmail;
        this.reservationNumber = reservationNumber;
        this.status = status;
    }

    @PrePersist
    public void prePersist() {
       this.reservationNumber = HexGenerator.generateKey(3);
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

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) && Objects.equals(flight, that.flight) && Objects.equals(seat, that.seat)
                && Objects.equals(userEmail, that.userEmail) && Objects.equals(reservationNumber, that.reservationNumber)
                && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flight, seat, userEmail, reservationNumber, status);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", flight=" + flight +
                ", seat=" + seat +
                ", userEmail='" + userEmail + '\'' +
                ", reservationNumber='" + reservationNumber + '\'' +
                ", status=" + status +
                '}';
    }
}
