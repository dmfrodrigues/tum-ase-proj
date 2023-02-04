package com.asedelivery.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.asedelivery.common.model.Role;

public abstract class Agent {
    @Id
    private String id;
    @Indexed(unique = true)
    public String username;

    public Agent(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    abstract public Role getRole();

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Agent) {
            Agent agent = (Agent) obj;
            return agent.getId().equals(getId());
        } else
            return false;
    }
}
