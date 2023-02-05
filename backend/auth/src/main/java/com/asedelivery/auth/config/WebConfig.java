package com.asedelivery.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// @EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Value("${frontend.address}")
    private static String ALLOWED_ORIGINS;
    private static final String ALLOWED_METHODS = "GET, PUT, POST, DELETE, OPTIONS, PATCH";
    private static final long MAX_AGE = 7200; // 2 hours (2 * 60 * 60)
    private static final String ALLOWED_HEADERS = "x-requested-with, authorization, Content-Type, Content-Length, Authorization, credential, X-XSRF-TOKEN, jwt, X-API-TOKEN";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(ALLOWED_ORIGINS)
            .allowCredentials(true)
            .allowedMethods(ALLOWED_METHODS)
            .maxAge(MAX_AGE)
            .allowedHeaders(ALLOWED_HEADERS);
    }
}
