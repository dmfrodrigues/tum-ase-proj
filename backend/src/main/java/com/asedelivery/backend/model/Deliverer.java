package com.asedelivery.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.asedelivery.backend.model.Principal.Role;

@Document("agent")
public class Deliverer extends Person {
    public Deliverer(String username, String name, String email) {
        super(username, name, email);
    }

    public Role getRole() {
        return Role.DELIVERER;
    }
}
