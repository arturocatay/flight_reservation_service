package com.vortech.flight.reservation.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ReservationEventListener {
    @KafkaListener(topics = "reservation-events", groupId = "reservation-group")
    public void listenReservationEvent(String message) {
        System.out.println("Received reservation event: " + message);
    }
}

