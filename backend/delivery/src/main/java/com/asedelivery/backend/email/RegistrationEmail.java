package com.asedelivery.backend.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;

public class RegistrationEmail extends Email {

    private final static String SUBJECT = "Your registration in ASEDelivery";

    public RegistrationEmail(
        JavaMailSender emailSender,
        SpringTemplateEngine templateEngine,
        String from,
        String email,
        String name,
        String username,
        String password,
        String loginUrl
    ) throws MessagingException {
        super(emailSender, templateEngine, "registration", from, email, SUBJECT);
        set("name", name);
        set("email", email);
        set("username", username);
        set("password", password);
        set("loginURL", loginUrl);
    }
}
