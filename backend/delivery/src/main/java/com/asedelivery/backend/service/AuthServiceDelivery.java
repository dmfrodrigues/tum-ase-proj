package com.asedelivery.backend.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

/**
 * The AuthService and AuthService thing is actually a workaround to avoid
 * circular Bean dependencies.
 */
@RestController
public class AuthServiceDelivery implements AuthService {

    private static final String AUTH_SERVICE_NAME = "auth";

    @Autowired
    private EurekaClient discoveryClient;

    public String authServiceUrl() {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka(AUTH_SERVICE_NAME, false);
        return instance.getHomePageUrl();
    }

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
            authServiceUrl() + "api/auth",
            entity,
            String.class
        );
        if(response.getStatusCode() != HttpStatus.OK){
            return response;
        }

        HttpHeaders responseHeaders = response.getHeaders();
        List<String> setCookies = responseHeaders.getOrEmpty(HttpHeaders.SET_COOKIE);
        Map<String, String> setCookiesMap = setCookies
            .stream()
            .map((s) -> s.split(";")[0])
            .collect(Collectors.toMap(
                (s) -> s.split("=")[0], 
                (s) -> s.split("=")[1]
        ));
        String jwt = setCookiesMap.get("jwt");

        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    public void putPrincipal(String jwt, String id, Role role, String username, String password) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, "jwt=" + jwt);

        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("role", role.toString());
        data.add("username", username);
        data.add("password", password);
        
        HttpEntity<MultiValueMap<String, String>> request =
            new HttpEntity<MultiValueMap<String, String>>(data, headers);

        ResponseEntity<String> response = restTemplate.exchange(
            authServiceUrl() + "api/auth/principal/" + id,
            HttpMethod.PUT,
            request,
            String.class
        );
        if(response.getStatusCode() != HttpStatus.OK){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, response.getBody());
        }
    }

    public void deletePrincipal(String jwt, String id) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, "jwt=" + jwt);
        
        HttpEntity<String> request = new HttpEntity<String>("", headers);

        ResponseEntity<String> response = restTemplate.exchange(
            authServiceUrl() + "api/auth/principal/" + id,
            HttpMethod.DELETE,
            request,
            String.class
        );
        if(response.getStatusCode() != HttpStatus.OK){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, response.getBody());
        }
    }
}
