package com.asedelivery.backend.Models;

import org.springframework.data.annotation.Id;

import com.asedelivery.backend.Models.Principal.Role;

public abstract class Agent {
    @Id
    private String id;
    private String username;

    public Agent(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    abstract public Role getRole();
}
