package com.asedelivery.backend.Models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("customer")
public class Customer extends Person {
    public Customer(String name, String password, String email){
        super(name, password, email);
    }
}
