package com.asedelivery.backend.Models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("customer")
public class Customer extends Person {
    public Customer(String name, String password, String email) {
        super(name, password, email);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Customer) {
            Customer customer = (Customer) obj;
            return customer.getId().equals(getId());
        } else return false;
    }
}
