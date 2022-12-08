package com.asedelivery.backend.Models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("deliverer")
public class Deliverer extends Person {
    public Deliverer(String name, String password, String email){
        super(name, password, email);
    }
}
