package com.asedelivery.backend.auth;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService extends UserDetailsService {

    ResponseEntity<String> authenticateUser(String authorization, HttpServletRequest request);

    ResponseEntity<String> authenticateUserWithApiToken(String string, HttpServletRequest request);

    void setAuthentication(String username, Collection<? extends GrantedAuthority> authorities);
    
}
