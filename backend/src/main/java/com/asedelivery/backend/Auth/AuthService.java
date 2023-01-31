package com.asedelivery.backend.Auth;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import com.asedelivery.backend.model.Principal;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class AuthService {
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private AseUserDetailsService userDetailsService;

    public ResponseEntity<String> authenticateUser(
        String authorization,
        HttpServletRequest request
    ) throws Exception {
        System.out.println("L42");

        // TODO: Get the username and password by decoding the Base64 credential inside
        // the Basic Authentication
        String headerString = authorization.substring("Basic".length()).trim();
        String decoded = new String(Base64.getDecoder().decode(headerString));
        String username = decoded.split(":", 2)[0];
        String password = decoded.split(":", 2)[1];

        // TODO: find if there is any user exists in the database based on the credential,
        // and authenticate the user using the Spring Authentication Manager
        UserDetails user = userDetailsService.loadUserByUsername(username);
        if (user == null) {
            return new ResponseEntity<>("User does not exist!", HttpStatus.UNAUTHORIZED);
        }

        Authentication auth = new UsernamePasswordAuthenticationToken(username, password);
        auth = authManager.authenticate(auth);
        if(auth == null){
            return new ResponseEntity<>("Password is incorrect", HttpStatus.UNAUTHORIZED);
        } else {
            SecurityContextHolder.getContext().setAuthentication(auth);
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        }

    }

    public Collection<? extends GrantedAuthority> getAuthorities(Principal principal) {
        List<GrantedAuthority> list = new ArrayList<>();

        list.add(new SimpleGrantedAuthority("ROLE_" + principal.getRole().toString()));

        return list;
    }
}

