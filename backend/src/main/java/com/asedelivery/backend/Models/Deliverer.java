package com.asedelivery.backend.Models;

import org.springframework.data.mongodb.core.mapping.Document;

import com.asedelivery.backend.Models.Principal.Role;

@Document("agent")
public class Deliverer extends Person {
    public Deliverer(String username, String name, String email) {
        super(username, name, email);
    }

    public Role getRole() {
        return Role.DELIVERER;
    }
}
