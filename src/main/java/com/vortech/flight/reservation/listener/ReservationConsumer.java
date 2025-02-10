package com.vortech.flight.reservation.listener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vortech.flight.reservation.constant.ReservationStatus;
import com.vortech.flight.reservation.exception.SendEmailReservationException;
import com.vortech.flight.reservation.model.entity.Reservation;
import com.vortech.flight.reservation.repository.ReservationRepository;
import com.vortech.flight.reservation.service.ReservationEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class ReservationConsumer {
    private final ReservationRepository reservationRepository;
    private final ReservationEmailService reservationEmailService;
    @Value("${app.send.email.active}")
    private String activeSend;

    @Autowired
    public ReservationConsumer (ReservationRepository reservationRepository, ReservationEmailService reservationEmailService) {
        this.reservationRepository = reservationRepository;
        this.reservationEmailService = reservationEmailService;
    }

    @KafkaListener(topics = {"${spring.kafka.topic.name}"}, groupId = "${spring.kafka.consumer.group-id}")
    public void listener(String jsonReservationString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Reservation reservation = gson.fromJson(jsonReservationString, Reservation.class);
        reservation = reservationRepository.save(reservation);
        //Send email after CONFIRMED reservation
        if(activeSend.equals("true") && reservation.getStatus().equals(ReservationStatus.CONFIRMED)) {
            try {
                reservationEmailService.sendEmail(reservation);
            } catch (SendEmailReservationException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
