package com.vortech.flight.reservation.repository;

import com.vortech.flight.reservation.constant.SeatStatus;
import com.vortech.flight.reservation.model.entity.Flight;
import com.vortech.flight.reservation.model.entity.Seat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Seat> findByFlightAndSeatNumber(Flight flight, String seatNumber);

    @Query("SELECT s FROM Seat s " +
            "JOIN s.flight f " +
            "LEFT JOIN Reservation r ON r.seat = s " +
            "WHERE f.flightNumber = :flightNumber " +
            "AND s.seatNumber = :seatNumber " +
            "AND (:reservationNumber IS NULL OR r.reservationNumber = :reservationNumber)")
    Optional<Seat> findByFlightNumberAndSeatNumberAndReservationNumber(
            @Param("flightNumber") String flightNumber,
            @Param("seatNumber") String seatNumber,
            @Param("reservationNumber") String reservationNumber);

    List<Seat> findByFlightAndStatus(Flight flight, SeatStatus status);
}
