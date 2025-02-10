package com.vortech.flight.reservation.controller;

import com.vortech.flight.reservation.dto.CanceledReservationResponse;
import com.vortech.flight.reservation.dto.ReservationResponse;
import com.vortech.flight.reservation.dto.SeatResponse;
import com.vortech.flight.reservation.exception.*;
import com.vortech.flight.reservation.factory.ResponseFactory;
import com.vortech.flight.reservation.model.entity.Reservation;
import com.vortech.flight.reservation.model.entity.Seat;
import com.vortech.flight.reservation.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final ResponseFactory responseFactory;

    public ReservationController(ReservationService reservationService, ResponseFactory responseFactory) {
        this.reservationService = reservationService;
        this.responseFactory = responseFactory;
    }

    @PostMapping("/reserve")
    public ResponseEntity<ReservationResponse> reserveSeat(@RequestParam String flightNumber, @RequestParam String seatNumber,
                                                           @RequestParam String userEmail) {
        try {
            Reservation reservation = reservationService.reserveSeat(flightNumber, seatNumber, userEmail);
            return responseFactory.createResponseForReserveSeat(reservation);
        } catch (FlightNotFoundException | FlightSeatNumberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ReservationResponse(e.getMessage(), Collections.emptyList()));
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<CanceledReservationResponse> cancelReservation(@RequestParam String flightNumber,
                                                                         @RequestParam String seatNumber,
                                                                         @RequestParam String reservationNumber) {
        try {
            reservationService.cancelReservation(flightNumber, seatNumber,reservationNumber);
            return responseFactory.createResponseForCancelReservation(flightNumber, seatNumber, reservationNumber);
        } catch (FlightNotFoundException | FlightSeatNumberNotFoundException | ReservationNotFoundException |
                 ReservationCanceledException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CanceledReservationResponse(e.getMessage()));
        }
    }

    @GetMapping("/reservation/{reservationNumber}")
    public ResponseEntity<ReservationResponse> getReservationByNumber(@PathVariable String reservationNumber) {
        Reservation reservation = reservationService.getReservationByReservationNumber(reservationNumber);
        return responseFactory.createResponseForGetReservationByNumber(reservation,reservationNumber);
    }

    @GetMapping("/flight/{flightNumber}")
    public ResponseEntity<ReservationResponse> getReservations(@PathVariable String flightNumber) {
        List<Reservation> reservations = reservationService.getReservationsByFlightNumber(flightNumber);
        return responseFactory.createResponseForGetReservations(reservations, flightNumber);
    }

    @GetMapping("/flight/{flightNumber}/available-seats")
    public ResponseEntity<SeatResponse> getAvailableSeats(@PathVariable String flightNumber) {
        try {
            List<Seat> seats = reservationService.getAvailableSeats(flightNumber);
            return responseFactory.createResponseForGetAvailableSeats(seats, flightNumber,"");
        } catch(FlightNotFoundException | NoSeatsAvailableException e) {
            return responseFactory.createResponseForGetAvailableSeats(Collections.emptyList(), flightNumber, e.getMessage());
        }
    }
}

