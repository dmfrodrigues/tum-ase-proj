package com.asedelivery.auth.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asedelivery.auth.service.AuthServiceAuth;
import com.asedelivery.common.auth.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthServiceAuth authService;

    @PostMapping
    public ResponseEntity<String> login(
        @RequestHeader("Authorization") Optional<String> authorization,
        @RequestHeader(AuthService.API_TOKEN_HEADER) Optional<String> apiToken,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("Request to /auth");

        try {
            ResponseEntity<String> responseEntity = null;
            if(authorization.isPresent()) responseEntity = authService.authenticateUser(authorization.get());
            else if(apiToken.isPresent()) responseEntity = authService.authenticateUserWithApiToken(apiToken.get());
            else return new ResponseEntity<>("Either Basic Auth or API Token must be provided", HttpStatus.BAD_REQUEST);
            
            if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
                String jwt = responseEntity.getBody();
                Cookie jwtCookie = new Cookie("jwt", jwt);
                // Configure the cookie to be HttpOnly and expires after a period
                // Then include the cookie into the response
                jwtCookie.setHttpOnly(true);
                jwtCookie.setSecure(false);
                jwtCookie.setPath("/");
                response.addCookie(jwtCookie);

                return new ResponseEntity<>(HttpStatus.OK);
            }
            
        } catch (UsernameNotFoundException e) {}
        return new ResponseEntity<>("Invalid Login", HttpStatus.UNAUTHORIZED);
    }
}
