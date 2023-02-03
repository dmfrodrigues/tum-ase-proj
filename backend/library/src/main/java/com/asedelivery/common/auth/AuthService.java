package com.asedelivery.common.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public interface AuthService {

    void setAuthentication(String username, Collection<? extends GrantedAuthority> authorities);
    
}
