package com.asedelivery.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.asedelivery.backend.model.Principal.Role;

@Document("agent")
public class Customer extends Person {
    public Customer(String username, String name, String email) {
        super(username, name, email);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Customer) {
            Customer customer = (Customer) obj;
            return customer.getId().equals(getId());
        } else
            return false;
    }

    public Role getRole() {
        return Role.CUSTOMER;
    }
}
