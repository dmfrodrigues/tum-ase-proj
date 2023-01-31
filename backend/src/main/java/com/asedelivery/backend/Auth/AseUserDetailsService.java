package com.asedelivery.backend.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.asedelivery.backend.Models.Principal;
import com.asedelivery.backend.Models.Repositories.PrincipalRepository;

public class AseUserDetailsService implements UserDetailsService {
    @Autowired
    private PrincipalRepository principalRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Principal principal = principalRepo.findByUsername(username);

        return User.withDefaultPasswordEncoder()
                .username(principal.getUsername())
                .password(principal.getPassword())
                .roles(principal.getRole().toString())
                .build();
    }
}
