package com.asedelivery.backend.Models;

import org.springframework.data.annotation.Id;

public class Agent {
    @Id
    private String id;

    private String password;

    public Agent(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }
}
