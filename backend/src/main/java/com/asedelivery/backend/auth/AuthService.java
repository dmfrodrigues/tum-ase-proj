package com.asedelivery.backend.auth;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    ResponseEntity<String> authenticateUser(String authorization);

    ResponseEntity<String> authenticateUserWithApiToken(String apiToken);

    void setAuthentication(String username, Collection<? extends GrantedAuthority> authorities);
    
}
