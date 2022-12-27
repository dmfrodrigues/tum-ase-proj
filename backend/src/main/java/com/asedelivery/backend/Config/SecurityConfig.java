package com.asedelivery.backend.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .and()
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests().requestMatchers("/**").authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement().disable();
                
        return http.build();
    }
}
