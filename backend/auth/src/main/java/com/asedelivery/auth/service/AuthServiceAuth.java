package com.asedelivery.auth.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

import com.asedelivery.auth.model.Principal;
import com.asedelivery.auth.model.Token;
import com.asedelivery.auth.model.repo.PrincipalRepository;
import com.asedelivery.auth.model.repo.TokenRepository;
import com.asedelivery.common.auth.AuthService;
import com.asedelivery.common.auth.jwt.JwtUtil;

/**
 * The AuthService and AuthService thing is actually a workaround to avoid
 * circular Bean dependencies.
 */
@RestController
public class AuthServiceAuth implements AuthService, UserDetailsService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private PrincipalRepository principalRepo;

    @Autowired
    private TokenRepository tokenRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Principal principal = principalRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));

        return User.withUsername(principal.getId())
                .password(principal.password)
                .roles(principal.role.toString())
                .build();
    }

    public UserDetails loadUserByApiToken(String apiToken) {
        Token token = tokenRepo.findById(apiToken)
            .orElseThrow(() -> new UsernameNotFoundException("token:" + apiToken));

        Principal principal = token.principal;

        return User.withUsername(principal.getId())
                .password(principal.password)
                .roles(principal.role.toString())
                .build();
    }

    public ResponseEntity<String> authenticateUser(String authorization) {
        // Get the username and password by decoding the Base64 credential inside
        // the Basic Authentication
        String headerString = authorization.substring("Basic".length()).trim();
        String decoded = new String(Base64.getDecoder().decode(headerString));
        String username = decoded.split(":", 2)[0];
        String password = decoded.split(":", 2)[1];

        // Find if there is any user exists in the database based on the credential,
        // and authenticate the user using the Spring Authentication Manager
        UserDetails user = loadUserByUsername(username);
        if (user == null) {
            return new ResponseEntity<>("User does not exist!", HttpStatus.UNAUTHORIZED);
        }

        if(bCryptPasswordEncoder.matches(password, user.getPassword())){
            final String jwt = jwtUtil.generateToken(user);
            return new ResponseEntity<String>(jwt, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(
                "Email or password is incorrect",
                HttpStatus.UNAUTHORIZED
            );
        }
    }

    public ResponseEntity<String> authenticateUserWithApiToken(String apiToken) {
        UserDetails user = loadUserByApiToken(apiToken);
        if (user == null) {
            return new ResponseEntity<>("User does not exist!", HttpStatus.UNAUTHORIZED);
        }

        final String jwt = jwtUtil.generateToken(user);
        return new ResponseEntity<String>(jwt, HttpStatus.OK);
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Principal principal) {
        List<GrantedAuthority> list = new ArrayList<>();

        list.add(new SimpleGrantedAuthority("ROLE_" + principal.role.toString()));

        return list;
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
}
