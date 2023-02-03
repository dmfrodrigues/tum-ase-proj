package com.asedelivery.common.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.asedelivery.common.auth.jwt.JwtUtil;
import com.asedelivery.common.auth.AuthService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthRequestFilter extends OncePerRequestFilter {

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
        String username = null;
        Collection<? extends GrantedAuthority> authorities = null;
        String jwt = null;
        final String authHeader = request.getHeader("Authorization");
        final String apiToken = request.getHeader(AuthService.API_TOKEN_HEADER);
        final Cookie[] cookies = request.getCookies();
        if(cookies != null){
            Map<String, String> cookiesMap = new HashMap<>();
            for(final Cookie cookie: cookies)
                cookiesMap.put(cookie.getName(), cookie.getValue());

            jwt = cookiesMap.get("jwt");
        }

        // System.out.println("Authenticate Header " + authHeader);
        if (jwt != null) {
            // If the JWT expires or not signed by us, send an error to the client
            if(!jwtUtil.verifyJwtSignature(jwt)){
                jwt = null;
            } else {
                // Extract the username from the JWT token.
                username = jwtUtil.extractUsername(jwt);
                authorities = jwtUtil.extractUserRoles(jwt);
            }
        } else {
            // No valid authentication, No go
            if (
                (authHeader == null || !authHeader.startsWith("Basic")) &&
                apiToken == null
            ) {
                response.sendError(HttpStatus.BAD_REQUEST.value(), "No JWT Token, API Token or Basic Auth Info Found");
            }
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load a user from the database that has the same username
            // as in the JWT token.
            authService.setAuthentication(username, authorities);
            // Authentication authContext = SecurityContextHolder.getContext().getAuthentication();
            // System.out.println(String.format("Authenticate Token Set:\n"
            //     + "Username: %s\n"
            //     + "Password: %s\n"
            //     + "Authority: %s\n",
            //     authContext.getPrincipal(),
            //     authContext.getCredentials(),
            //     authContext.getAuthorities().toString())
            // );
        }
        filterChain.doFilter(request, response);
    }
}
