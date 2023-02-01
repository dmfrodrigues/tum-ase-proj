package com.asedelivery.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.asedelivery.backend.model.Principal.Role;

@Document("agent")
public class Box extends Agent {
    public String address;

    @DocumentReference
    public Customer customer;

    public Box(String username, String address) {
        super(username);
        this.address = address;
    }

    public Role getRole() {
        return Role.BOX;
    }
}
