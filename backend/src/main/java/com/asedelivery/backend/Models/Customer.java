package com.asedelivery.backend.Models;

import org.springframework.data.mongodb.core.mapping.Document;

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

    public String getRole() {
        return "CUSTOMER";
    }
}
