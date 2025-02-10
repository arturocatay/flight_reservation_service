package com.vortech.flight.reservation.service;

import com.vortech.flight.reservation.exception.SendEmailReservationException;
import com.vortech.flight.reservation.model.entity.Reservation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class ReservationEmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}") // Email sender address
    private String senderEmail;

    @Autowired
    public ReservationEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(Reservation reservation) throws SendEmailReservationException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(senderEmail);
            helper.setTo(reservation.getUserEmail());
            helper.setSubject("Your Reservation is Confirmed!");

            String emailBody = buildEmailBody(reservation);
            helper.setText(emailBody, true);

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new SendEmailReservationException(e.getMessage());
        }
    }

    private String buildEmailBody(Reservation reservation) {
        return "<html><body>" +
                "<h2>Your Reservation is Confirmed!</h2>" +
                "<p>Dear Customer,</p>" +
                "<p>Your reservation details are as follows:</p>" +
                "<ul>" +
                "<li><b>Reservation Number:</b> " + reservation.getReservationNumber() + "</li>" +
                "<li><b>Flight Number:</b> " + reservation.getFlight().getFlightNumber() + "</li>" +
                "<li><b>Seat Number:</b> " + reservation.getSeat().getSeatNumber() + "</li>" +
                "<li><b>Departure:</b> " + reservation.getFlight().getDeparture() + "</li>" +
                "<li><b>Destination:</b> " + reservation.getFlight().getDestination() + "</li>" +
                "<li><b>Time:</b> " + reservation.getFlight().getDepartureTime() + "</li>" +
                "</ul>" +
                "<p>Thank you for choosing us!</p>" +
                "</body></html>";
    }
}
