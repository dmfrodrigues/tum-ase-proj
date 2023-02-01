package com.asedelivery.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.asedelivery.backend.service.EmailService;

@Controller
public class MyController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/sendmail")
    public ResponseEntity<String> sendmail() {

        emailService.sendMail("kate@example.com", "Test Subject", "Test mail");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
