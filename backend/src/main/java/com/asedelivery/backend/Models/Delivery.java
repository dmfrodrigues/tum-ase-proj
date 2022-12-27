package com.asedelivery.backend.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document("delivery")
public class Delivery {
    @Id
    private String id;

    @DocumentReference
    private Customer customer;

    @DocumentReference
    private Box box;

    @DocumentReference
    private Dispatcher createdBy;

    @DocumentReference
    private Deliverer deliverer;

    private String pickupAddress;

    public Delivery(Customer customer, Dispatcher createdBy, Deliverer deliverer, String pickupAddress, Box box){
        assert box.getCustomer().equals(customer);

        this.customer = customer;
        this.createdBy = createdBy;
        this.deliverer = deliverer;
        this.pickupAddress = pickupAddress;
        this.box = box;
    }
}
