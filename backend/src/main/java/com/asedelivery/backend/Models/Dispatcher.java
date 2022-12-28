package com.asedelivery.backend.Models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("agent")
public class Dispatcher extends Person {
    public Dispatcher(String username, String name, String email){
        super(username, name, email);
    }
}
