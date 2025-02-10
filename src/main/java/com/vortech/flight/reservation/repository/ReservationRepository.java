package com.vortech.flight.reservation.repository;

import com.vortech.flight.reservation.model.entity.Flight;
import com.vortech.flight.reservation.model.entity.Reservation;
import com.vortech.flight.reservation.model.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findByFlight(Flight flight);
    Optional<Reservation> findByFlightAndSeat(Flight flight, Seat seat);
    Optional<Reservation> findByFlightAndSeatAndReservationNumber(Flight flight, Seat seat, String reservationNumber);
    @Query("SELECT r FROM Reservation r WHERE r.flight.flightNumber = :flightNumber")
    List<Reservation> findReservationsByFlightNumber(@Param("flightNumber") String flightNumber);
    Optional<Reservation> findByReservationNumber(String reservationNumber);
}
