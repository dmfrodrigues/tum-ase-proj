package com.asedelivery.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.asedelivery.common.model.Role;

public abstract class Agent {
    @Id
    private String id;
    @Indexed(unique = true)
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
