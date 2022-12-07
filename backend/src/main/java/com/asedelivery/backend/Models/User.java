package com.asedelivery.backend.Models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
public class User extends Agent {
    private String name;
    private String email;

    public User(String name, String password, String email) {
        super(password);
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
