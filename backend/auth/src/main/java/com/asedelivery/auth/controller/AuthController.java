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
import org.springframework.web.server.ResponseStatusException;

import com.asedelivery.auth.model.Principal;
import com.asedelivery.auth.model.repo.PrincipalRepository;
import com.asedelivery.auth.service.AuthServiceAuth;
import com.asedelivery.common.auth.AuthService;
import com.asedelivery.common.auth.jwt.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthServiceAuth authService;

    @Autowired
    private PrincipalRepository principalRepo;

    @PostMapping
    public ResponseEntity<Object> login(
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

                String id = jwtUtil.extractUsername(jwt);
                Principal principal = principalRepo.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Authenticated, but could not find principal; contact admin"));

                Cookie jwtCookie = new Cookie("jwt", jwt);
                // Configure the cookie to be HttpOnly and expires after a period
                // Then include the cookie into the response
                jwtCookie.setHttpOnly(true);
                jwtCookie.setSecure(false);
                jwtCookie.setPath("/");
                jwtCookie.setMaxAge((int)(JwtUtil.EXPIRATION_MILLIS / 1000));
                response.addCookie(jwtCookie);

                return new ResponseEntity<>(principal, HttpStatus.OK);
            }
            
        } catch (UsernameNotFoundException e) {}
        return new ResponseEntity<>("Invalid Login", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
        HttpServletRequest request,
        HttpServletResponse response
    ){
        Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);

        response.addCookie(jwtCookie);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
