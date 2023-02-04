package com.asedelivery.backend.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;

public class ModifiedPersonEmail extends Email {

    private final static String SUBJECT = "Your registration in ASEDelivery";

    public ModifiedPersonEmail(
        JavaMailSender emailSender,
        SpringTemplateEngine templateEngine,
        String from,
        String email,
        String name,
        String oldUsername, String newUsername,
        String oldName, String newName,
        String oldEmail, String newEmail,
        String newPassword
    ) throws MessagingException {
        super(emailSender, templateEngine, "modified-person", from, email, SUBJECT);
        set("name", name);
        set("name", oldName + " &rarr; " + newName);
        set("email", oldEmail + " &rarr; " + newEmail);
        set("username", oldUsername + " &rarr; " + newUsername);
        set("password", newPassword);
    }
}
