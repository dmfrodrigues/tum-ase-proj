package com.asedelivery.backend.service;

import java.util.Collection;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.asedelivery.common.auth.AuthService;
import com.asedelivery.common.model.Role;

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

    public void putPrincipal(String jwt, String id, Role role, String username, String password) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, "jwt=" + jwt);

        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("id", id);
        data.add("role", role.toString());
        data.add("username", username);
        data.add("password", password);
        
        HttpEntity<MultiValueMap<String, String>> request =
            new HttpEntity<MultiValueMap<String, String>>(data, headers);

        ResponseEntity<String> response = restTemplate.exchange(
            "http://auth/principal",
            HttpMethod.PUT,
            request,
            String.class
        );
        if(response.getStatusCode() != HttpStatus.OK){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, response.getBody());
        }
        System.err.println("TODO: REMOVE ME. Response to putting principal is " + response.getBody());
    }
}
