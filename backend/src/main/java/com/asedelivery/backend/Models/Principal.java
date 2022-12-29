package com.asedelivery.backend.Models;

import org.springframework.data.annotation.Id;

public class Principal {
    @Id
    private String id;

    private String username;

    private String password;

    public Principal(String id, String username, String password) {
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
