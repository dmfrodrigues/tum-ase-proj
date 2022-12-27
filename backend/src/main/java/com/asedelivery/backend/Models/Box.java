package com.asedelivery.backend.Models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document("box")
public class Box extends Principal {
    private String name;
    private String address;

    @DocumentReference
    private Customer customer;

    public Box(String name, String password, String address){
        super(password);
        this.name = name;
        this.address = address;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
