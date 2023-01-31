package com.asedelivery.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.asedelivery.backend.Auth.AseUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain genericFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .and()
                .csrf().disable()
                .authorizeHttpRequests()
                    .requestMatchers("/**").authenticated()
                    .requestMatchers("/auth/**").permitAll()
                .and()
                .sessionManagement().disable();

        return http.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .securityMatcher("/auth/**")
                .authorizeHttpRequests()
                    .requestMatchers("/auth/**").permitAll()
                .and()
                .httpBasic()
                .and()
                .sessionManagement().disable();

        return http.build();
    }

    @Bean
	public UserDetailsService userDetailsService() {
		return new AseUserDetailsService();
	}

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // @Bean
    // public BCryptPasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }
}
