package com.vortech.flight.reservation.factory;

import com.vortech.flight.reservation.dto.CanceledReservationResponse;
import com.vortech.flight.reservation.dto.ReservationResponse;
import com.vortech.flight.reservation.dto.SeatResponse;
import com.vortech.flight.reservation.model.entity.Reservation;
import com.vortech.flight.reservation.model.entity.Seat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import java.util.Collections;
import java.util.List;

@Component
public class ResponseFactory {

    public ResponseEntity<ReservationResponse> createResponseForGetReservations(List<Reservation> reservations, String flightNumber) {
        String message = "Reservations found";
        if (reservations.isEmpty()) {
            message = "No reservations found for flight number " + flightNumber;
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ReservationResponse(message, reservations));
    }

    public ResponseEntity<ReservationResponse> createResponseForGetReservationByNumber(Reservation reservation, String reservationNummber) {
        String message = "Reservation found";
        if (reservation == null) {
            message = "No reservation found for reservation number " + reservationNummber;
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ReservationResponse(message, Collections.singletonList(reservation)));
    }

    public ResponseEntity<SeatResponse> createResponseForGetAvailableSeats(List<Seat> seats, String flightNumber, String message) {
        if(seats.isEmpty()){
            message ="Seats not found for flight number " +flightNumber ;
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SeatResponse(message, seats));
    }

    public ResponseEntity<ReservationResponse> createResponseForReserveSeat(Reservation reservation) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ReservationResponse("Reservation successful!", Collections.singletonList(reservation)));

    }

    public ResponseEntity<CanceledReservationResponse> createResponseForCancelReservation(String flightNumber,
                                                                                          String seatNumber,
                                                                                          String reservationNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(new CanceledReservationResponse("Reservation number "
                + reservationNumber + " canceled for flight "+ flightNumber + ", seat number "+seatNumber));
    }

}