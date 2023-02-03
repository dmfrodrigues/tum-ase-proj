package com.asedelivery.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.asedelivery.common.model.Role;

@Document("agent")
public class Deliverer extends Person {
    public Deliverer(String username, String name, String email) {
        super(username, name, email);
    }

    public Role getRole() {
        return Role.DELIVERER;
    }
}
