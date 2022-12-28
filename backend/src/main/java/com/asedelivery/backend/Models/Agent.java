package com.asedelivery.backend.Models;

import org.springframework.data.annotation.Id;

public class Agent {
    @Id
    private String id;
    private String username;

    public Agent(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getUsername(){
        return username;
    }
}
