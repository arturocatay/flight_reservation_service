package com.vortech.flight.reservation.listener;

import com.google.gson.Gson;
import com.vortech.flight.reservation.model.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class ReservationProvider {
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${spring.kafka.topic.name}")
    private String topiName;

    @Autowired
    public ReservationProvider(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produceMessage(Reservation reservation) {
        Gson gson = new Gson();
        String jsonReservationString = gson.toJson(reservation);
        kafkaTemplate.send(topiName, jsonReservationString);
    }
}
