package com.asedelivery.backend.Models.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asedelivery.backend.Models.Deliverer;

public interface DelivererRepository extends MongoRepository<Deliverer, String> {

}
