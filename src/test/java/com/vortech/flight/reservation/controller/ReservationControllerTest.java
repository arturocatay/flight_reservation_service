package com.vortech.flight.reservation.controller;

import com.vortech.flight.reservation.dto.CanceledReservationResponse;
import com.vortech.flight.reservation.dto.ReservationResponse;
import com.vortech.flight.reservation.dto.SeatResponse;
import com.vortech.flight.reservation.exception.FlightNotFoundException;
import com.vortech.flight.reservation.exception.NoSeatsAvailableException;
import com.vortech.flight.reservation.exception.ReservationNotFoundException;
import com.vortech.flight.reservation.factory.ResponseFactory;
import com.vortech.flight.reservation.model.entity.Reservation;
import com.vortech.flight.reservation.model.entity.Seat;
import com.vortech.flight.reservation.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private ResponseFactory responseFactory;

    private static final String BASE_URL = "/reservations";

    @WithMockUser(username = "test@example.com", roles = {"USER"})
    @Test
    void getReservationByNumber_ShouldReturnReservation_WhenReservationExists() throws Exception {
        String reservationNumber = "RES12345";
        Reservation mockReservation = new Reservation();
        ReservationResponse mockResponse = new ReservationResponse("Reservation found!", List.of(mockReservation));

        when(reservationService.getReservationByReservationNumber(reservationNumber)).thenReturn(mockReservation);
        when(responseFactory.createResponseForGetReservationByNumber(mockReservation, reservationNumber))
                .thenReturn(ResponseEntity.ok(mockResponse));

        mockMvc.perform(get(BASE_URL + "/reservation/{reservationNumber}", reservationNumber)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Reservation found!"));
    }

    @WithMockUser(username = "test@example.com", roles = {"USER"})
    @Test
    void reserveSeat_ShouldReturnCreated_WhenReservationSuccessful() throws Exception {
        String flightNumber = "AA101";
        String seatNumber = "12A";
        String userEmail = "test@example.com";

        Reservation mockReservation = new Reservation();
        ReservationResponse mockResponse = new ReservationResponse("Reservation successful!!", List.of(mockReservation));

        when(reservationService.reserveSeat(flightNumber, seatNumber, userEmail)).thenReturn(mockReservation);
        when(responseFactory.createResponseForReserveSeat(mockReservation)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(mockResponse));

        mockMvc.perform(post(BASE_URL + "/reserve")
                        .param("flightNumber", flightNumber)
                        .param("seatNumber", seatNumber)
                        .param("userEmail", userEmail)
                .with(csrf())
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Reservation successful!!"));
    }

    @WithMockUser(username = "test@example.com", roles = {"USER"})
    @Test
    void reserveSeat_ShouldReturnBadRequest_WhenFlightNotFound() throws Exception {
        String flightNumber = "XX999";
        String seatNumber = "15B";
        String userEmail = "test@example.com";

        when(reservationService.reserveSeat(flightNumber, seatNumber, userEmail))
                .thenThrow(new FlightNotFoundException("Flight not found"));

        mockMvc.perform(post(BASE_URL + "/reserve")
                        .param("flightNumber", flightNumber)
                        .param("seatNumber", seatNumber)
                        .param("userEmail", userEmail)
                        .with(csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Flight not found"));
    }

    @WithMockUser(username = "test@example.com", roles = {"USER"})
    @Test
    void cancelReservation_ShouldReturnOk_WhenCancellationSuccessful() throws Exception {
        String flightNumber = "AA101";
        String seatNumber = "12A";
        String reservationNumber = "RES12345";

        CanceledReservationResponse mockResponse = new CanceledReservationResponse("Reservation canceled successfully");

        when(responseFactory.createResponseForCancelReservation(flightNumber, seatNumber, reservationNumber))
                .thenReturn(ResponseEntity.ok(mockResponse));

        mockMvc.perform(post(BASE_URL + "/cancel")
                        .param("flightNumber", flightNumber)
                        .param("seatNumber", seatNumber)
                        .param("reservationNumber", reservationNumber)
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Reservation canceled successfully"));
    }

    @WithMockUser(username = "test@example.com", roles = {"USER"})
    @Test
    void cancelReservation_ShouldReturnBadRequest_WhenReservationNotFound() throws Exception {
        String flightNumber = "XX999";
        String seatNumber = "15B";
        String reservationNumber = "RES99999";

        doThrow(new ReservationNotFoundException("Reservation not found"))
                .when(reservationService)
                .cancelReservation(flightNumber, seatNumber, reservationNumber);

        mockMvc.perform(post(BASE_URL + "/cancel")
                        .param("flightNumber", flightNumber)
                        .param("seatNumber", seatNumber)
                        .param("reservationNumber", reservationNumber)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Reservation not found"));
    }

    @WithMockUser(username = "test@example.com", roles = {"USER"})
    @Test
    void getReservations_ShouldReturnReservationsList() throws Exception {
        String flightNumber = "AA101";
        List<Reservation> reservations = List.of(new Reservation());

        ReservationResponse mockResponse = new ReservationResponse("Reservations retrieved", reservations);

        when(reservationService.getReservationsByFlightNumber(flightNumber)).thenReturn(reservations);
        when(responseFactory.createResponseForGetReservations(reservations, flightNumber))
                .thenReturn(ResponseEntity.ok(mockResponse));

        mockMvc.perform(get(BASE_URL + "/flight/{flightNumber}", flightNumber)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Reservations retrieved"));
    }

    @WithMockUser(username = "test@example.com", roles = {"USER"})
    @Test
    void getAvailableSeats_ShouldReturnSeatsList() throws Exception {
        String flightNumber = "AA101";
        List<Seat> availableSeats = List.of(new Seat());

        SeatResponse mockResponse = new SeatResponse("Available seats retrieved", availableSeats);

        when(reservationService.getAvailableSeats(flightNumber)).thenReturn(availableSeats);
        when(responseFactory.createResponseForGetAvailableSeats(availableSeats, flightNumber, ""))
                .thenReturn(ResponseEntity.ok(mockResponse));

        mockMvc.perform(get(BASE_URL + "/flight/{flightNumber}/available-seats", flightNumber)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Available seats retrieved"));
    }

    @WithMockUser(username = "test@example.com", roles = {"USER"})
    @Test
    void getAvailableSeats_ShouldReturnBadRequest_WhenNoSeatsAvailable() throws Exception {
        String flightNumber = "";

        when(reservationService.getAvailableSeats(flightNumber))
                .thenThrow(new NoSeatsAvailableException("No seats available"));

        mockMvc.perform(get(BASE_URL + "/flight/{flightNumber}/available-seats", flightNumber)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }
}

