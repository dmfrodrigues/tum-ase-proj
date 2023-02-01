package com.asedelivery.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asedelivery.backend.auth.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<String> login(
        @RequestHeader("Authorization") String authorization,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("Request to /auth");

        try {
            return authService.authenticateUser(authorization, request);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid Login", HttpStatus.UNAUTHORIZED);
        }
    }
}
