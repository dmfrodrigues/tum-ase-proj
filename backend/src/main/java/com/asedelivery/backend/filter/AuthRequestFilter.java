package com.asedelivery.backend.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.asedelivery.backend.auth.AuthService;
import com.asedelivery.backend.auth.jwt.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// @Component
public class AuthRequestFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        System.out.println("L38");

        String username = null;
        String jwt = null;
        final String authHeader = request.getHeader("Authorization");
        System.out.println("Authenticate Header " + authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            // TODO: Get the JWT in the header.
            String[] authHeaderSplit = authHeader.split(" ");
            if(authHeaderSplit.length >= 2){
                jwt = authHeaderSplit[1];
            
                // If the JWT expires or not signed by us, send an error to the client
                if(!jwtUtil.verifyJwtSignature(jwt)){
                    jwt = null;
                } else {
                    // Extract the username from the JWT token.
                    username = jwtUtil.extractUsername(jwt);
                }
            }
        } else {
            // No valid authentication, No go
            if (authHeader == null || !authHeader.startsWith("Basic")) {
                response.sendError(HttpStatus.BAD_REQUEST.value(), "No JWT Token or Basic Auth Info Found");
            }
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load a user from the database that has the same username
            // as in the JWT token.
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            authService.setAuthentication(userDetails, request);
            Authentication authContext = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(String.format("Authenticate Token Set:\n"
                + "Username: %s\n"
                + "Password: %s\n"
                + "Authority: %s\n",
                authContext.getPrincipal(),
                authContext.getCredentials(),
                authContext.getAuthorities().toString())
            );
        }
        filterChain.doFilter(request, response);
    }
}
