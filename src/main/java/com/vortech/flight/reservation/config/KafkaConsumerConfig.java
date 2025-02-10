package com.vortech.flight.reservation.config;


import com.vortech.flight.reservation.model.entity.Reservation;
import com.vortech.flight.reservation.template.KafkaConfigTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@Configuration
public class KafkaConsumerConfig extends KafkaConfigTemplate {


    @Bean
    public ConsumerFactory<String, Reservation> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Reservation>> consumer(ConsumerFactory consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Reservation> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
