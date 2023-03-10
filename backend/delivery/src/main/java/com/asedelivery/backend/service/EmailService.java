package com.asedelivery.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.asedelivery.backend.email.CompletedDeliveryEmail;
import com.asedelivery.backend.email.DeliveredDeliveryEmail;
import com.asedelivery.backend.email.Email;
import com.asedelivery.backend.email.ModifiedPersonEmail;
import com.asedelivery.backend.email.NewDeliveryEmail;
import com.asedelivery.backend.email.RegistrationEmail;
import com.asedelivery.backend.model.Delivery;

import jakarta.mail.MessagingException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${spring.mail.from}")
    private String FROM;

    @Value("${frontend.address}")
    private String FRONTEND_URL;

    public EmailService(){
    }

    public RegistrationEmail createRegistrationEmail(
        String email,
        String name,
        String username,
        String password
    ) throws MessagingException {
        String LOGIN_URL = FRONTEND_URL + "/login";

        return new RegistrationEmail(
            emailSender,
            templateEngine,
            FROM,
            email,
            name,
            username,
            password,
            LOGIN_URL
        );
    }

    public Email createModifiedPersonEmail(
        String email,
        String name,
        String oldUsername, String newUsername,
        String oldName, String newName,
        String oldEmail, String newEmail,
        String newPassword
    ) throws MessagingException {
        return new ModifiedPersonEmail(
            emailSender,
            templateEngine,
            FROM,
            email,
            name,
            oldUsername, newUsername,
            oldName, newName,
            oldEmail, newEmail,
            newPassword
        );
    }

    public Email createNewDeliveryEmail(
        String email,
        String name,
        Delivery delivery
    ) throws MessagingException {
        String DELIVERY_URL = FRONTEND_URL + "/delivery/" + delivery.getId();

        return new NewDeliveryEmail(
            emailSender,
            templateEngine,
            FROM,
            email,
            name,
            delivery,
            DELIVERY_URL
        );
    }

    public Email createDeliveryDeliveredEmail(
        String email,
        String name,
        Delivery delivery
    ) throws MessagingException {
        String DELIVERY_URL = FRONTEND_URL + "/delivery/" + delivery.getId();

        return new DeliveredDeliveryEmail(
            emailSender,
            templateEngine,
            FROM,
            email,
            name,
            delivery,
            DELIVERY_URL
        );
    }

    public Email createCompletedDeliveredEmail(String email, String name, Delivery delivery) throws MessagingException {
        return new CompletedDeliveryEmail(
            emailSender,
            templateEngine,
            FROM,
            email,
            name,
            delivery
        );
    }
}
