package com.asedelivery.backend.Models;

import org.springframework.data.annotation.Id;

import com.asedelivery.backend.Auth.Password;

public class Principal {
    @Id
    private String id;

    private String username;

    private Password password;

    public Principal(String id, String username, Password password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String token) {
        return token.equals(password);
    }
}
