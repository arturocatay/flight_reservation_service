package com.vortech.flight.reservation.service;

import com.vortech.flight.reservation.constant.ReservationStatus;
import com.vortech.flight.reservation.constant.SeatStatus;
import com.vortech.flight.reservation.exception.*;
import com.vortech.flight.reservation.listener.ReservationProvider;
import com.vortech.flight.reservation.model.entity.Flight;
import com.vortech.flight.reservation.model.entity.Reservation;
import com.vortech.flight.reservation.model.entity.Seat;
import com.vortech.flight.reservation.repository.FlightRepository;
import com.vortech.flight.reservation.repository.ReservationRepository;
import com.vortech.flight.reservation.repository.SeatRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ReservationService {
    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationProvider reservationProvider;

    public ReservationService(FlightRepository flightRepository, SeatRepository seatRepository,
                              ReservationRepository reservationRepository, ReservationProvider reservationProvider) {
        this.flightRepository = flightRepository;
        this.seatRepository = seatRepository;
        this.reservationRepository = reservationRepository;
        this.reservationProvider = reservationProvider;
    }

    @Transactional
    @CircuitBreaker(name = "flight_reservation_service", fallbackMethod = "reserveSeatFallback")
    public Reservation reserveSeat(String flightNumber, String seatNumber, String userEmail) {
        Flight flight = validateFlightExistence(flightNumber);
        Seat seat = validateSeatAvailability(flight, seatNumber);

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        seat.setStatus(SeatStatus.RESERVED);

        Reservation reservation = createReservation(flight, seat, userEmail);
        sendReservationEvent(reservation);

        return reservation;
    }

    @Transactional
    @CircuitBreaker(name = "flight_reservation_service", fallbackMethod = "cancelReservationFallback")
    public void cancelReservation(String flightNumber, String seatNumber, String reservationNumber) {
        Flight flight = validateFlightExistence(flightNumber);
        Seat seat = validateSeatForCancel(flight.getFlightNumber(), seatNumber, reservationNumber);
        Reservation reservation = validateReservationExistence(flight, seat, reservationNumber);

        if (reservation.getStatus() == ReservationStatus.CANCELED) {
            throw new ReservationCanceledException("Reservation already canceled");
        }

        reservation.setStatus(ReservationStatus.CANCELED);
        seat.setStatus(SeatStatus.AVAILABLE);
        flight.setAvailableSeats(flight.getAvailableSeats() + 1);

        saveEntities(reservation, seat, flight);
    }

    @CircuitBreaker(name = "flight_reservation_service", fallbackMethod = "getAvailableSeatsFallback")
    public List<Seat> getAvailableSeats(String flightNumber) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with flight number: " + flightNumber));

        List<Seat> availableSeats = seatRepository.findByFlightAndStatus(flight, SeatStatus.AVAILABLE);

        if (availableSeats.isEmpty()) {
            throw new NoSeatsAvailableException("No available seats for flight number: " + flightNumber);
        }

        return availableSeats;
    }

    @CircuitBreaker(name = "flight_reservation_service", fallbackMethod = "getReservationsByFlightNumberFallback")
    public List<Reservation> getReservationsByFlightNumber(String flightNumber) {
        return reservationRepository.findReservationsByFlightNumber(flightNumber);
    }

    @CircuitBreaker(name = "flight_reservation_service", fallbackMethod = "getReservationByReservationNumberFallback")
    public Reservation getReservationByReservationNumber(String reservationNumber) {
        return reservationRepository.findByReservationNumber(reservationNumber)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found for reservation number :"
                        + reservationNumber));
    }

    private Flight validateFlightExistence(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with flight number: " + flightNumber));
    }

    private Seat validateSeatAvailability(Flight flight, String seatNumber) {
        return seatRepository.findByFlightAndSeatNumber(flight, seatNumber)
                    .filter(seat -> seat.getStatus() != SeatStatus.RESERVED)
                    .orElseThrow(() -> new FlightSeatNumberNotFoundException("Seat number " + seatNumber + " " +
                            "not available for flight number: " + flight.getFlightNumber()));

    }

    private Seat validateSeatForCancel(String flightNumber, String seatNumber, String reservationNumber) {
        return seatRepository.findByFlightNumberAndSeatNumberAndReservationNumber(flightNumber, seatNumber, reservationNumber)
                .orElseThrow(() -> new FlightSeatNumberNotFoundException(
                        "Seat number " + seatNumber + " not available for flight "
                                + flightNumber + " with reservation number " + reservationNumber));
    }

    private Reservation validateReservationExistence(Flight flight, Seat seat, String reservationNumber) {
        return reservationRepository.findByFlightAndSeatAndReservationNumber(flight, seat, reservationNumber)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
    }

    private Reservation createReservation(Flight flight, Seat seat, String userEmail) {
        Reservation reservation = new Reservation();
        reservation.setFlight(flight);
        reservation.setSeat(seat);
        reservation.setUserEmail(userEmail);
        reservation.setStatus(ReservationStatus.CONFIRMED);

        saveEntities(reservation, seat, flight);

        return reservation;
    }

    private void sendReservationEvent(Reservation reservation) {
        reservationProvider.produceMessage(reservation);
    }

    private void saveEntities(Reservation reservation, Seat seat, Flight flight) {
        reservationRepository.save(reservation);
        seatRepository.save(seat);
        flightRepository.save(flight);
    }

    // Fallback methods for CircuitBreaker

    public Reservation reserveSeatFallback(String flightNumber, String seatNumber, String userEmail, Throwable throwable) {
        throw new ReservationFailedException("Failed to reserve seat. Please try again later.");
    }

    public void cancelReservationFallback(String flightNumber, String seatNumber, Throwable throwable) {
        throw new ReservationFailedException("Failed to cancel reservation. Please try again later.");
    }

    public List<Seat> getAvailableSeatsFallback(String flightNumber, Throwable throwable) {
        throw new NoSeatsAvailableException("Unable to fetch available seats. Please try again later.");
    }

    public List<Reservation> getReservationsByFlightNumberFallback(String flightNumber, Throwable throwable) {
        throw new ReservationFailedException("Unable to fetch reservation. Please try again later.");
    }

    public List<Reservation> getReservationsByReservationNumberFallback(String reservationNumber, Throwable throwable) {
        throw new ReservationFailedException("Unable to fetch reservation by number. Please try again later.");
    }
}