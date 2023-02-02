package com.asedelivery.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.asedelivery.backend.email.RegistrationEmail;

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

        System.out.println("FRONTEND_URL=" + FRONTEND_URL);
        System.out.println("LOGIN_URL=" + LOGIN_URL);
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
}
