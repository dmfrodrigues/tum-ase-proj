package com.asedelivery.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.asedelivery.common.model.Role;

@Document("agent")
public class Customer extends Person {
    public Customer(String username, String name, String email) {
        super(username, name, email);
    }

    public Role getRole() {
        return Role.CUSTOMER;
    }
}
