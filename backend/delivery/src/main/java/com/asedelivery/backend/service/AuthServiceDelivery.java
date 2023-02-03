package com.asedelivery.backend.service;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

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
}
