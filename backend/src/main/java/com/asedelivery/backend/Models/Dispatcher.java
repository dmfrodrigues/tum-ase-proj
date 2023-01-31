package com.asedelivery.backend.Models;

import org.springframework.data.mongodb.core.mapping.Document;

import com.asedelivery.backend.Models.Principal.Role;

@Document("agent")
public class Dispatcher extends Person {
    public Dispatcher(String username, String name, String email) {
        super(username, name, email);
    }

    public Role getRole() {
        return Role.DISPATCHER;
    }
}
