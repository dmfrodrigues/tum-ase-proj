package com.asedelivery.backend.Models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("dispatcher")
public class Dispatcher extends Person {
    public Dispatcher(String name, String password, String email){
        super(name, password, email);
    }
}
