package com.asedelivery.backend.Models.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asedelivery.backend.Models.Delivery;

public interface DeliveryRepository extends MongoRepository<Delivery, String> {

}
