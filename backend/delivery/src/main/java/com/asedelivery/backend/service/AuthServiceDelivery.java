package com.asedelivery.backend.service;

import java.util.Collection;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.asedelivery.common.auth.AuthService;

/**
 * The AuthService and AuthService thing is actually a workaround to avoid
 * circular Bean dependencies.
 */
@RestController
public class AuthServiceDelivery implements AuthService {

    public void setAuthentication(String username, Collection<? extends GrantedAuthority> authorities) {
        PreAuthenticatedAuthenticationToken token =
            new PreAuthenticatedAuthenticationToken(
                username,
                "[Protected]",
                authorities
            );
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public ResponseEntity<String> authenticateUserWithApiToken(String token) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set(API_TOKEN_HEADER, token);
        
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ResponseEntity<String> response = restTemplate.postForEntity(
            "http://auth/auth",
            entity,
            String.class
        );

        return response;
    }
}
