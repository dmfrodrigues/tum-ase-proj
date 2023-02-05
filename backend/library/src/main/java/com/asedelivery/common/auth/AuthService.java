package com.asedelivery.common.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public interface AuthService {

    static public final String API_TOKEN_HEADER = "X-API-TOKEN";

    void setAuthentication(String username, Collection<? extends GrantedAuthority> authorities);
    
}
