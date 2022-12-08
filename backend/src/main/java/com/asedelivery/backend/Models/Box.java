package com.asedelivery.backend.Models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("box")
public class Box extends Agent {
    private String name;
    private String address;

    public Box(String name, String password, String address){
        super(password);
        this.name = name;
        this.address = address;
    }
}
