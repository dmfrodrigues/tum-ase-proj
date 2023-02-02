package com.asedelivery.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.asedelivery.backend.service.EmailService;

import jakarta.mail.MessagingException;

@Controller
public class MyController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/sendmail")
    public ResponseEntity<String> sendmail() {

        try {
            emailService.sendMail("kate@example.com", "Test Subject", "Test mail");
        } catch (MessagingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
