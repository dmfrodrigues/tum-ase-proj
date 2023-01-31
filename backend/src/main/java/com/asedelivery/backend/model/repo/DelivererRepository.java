package com.asedelivery.backend.model.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asedelivery.backend.model.Deliverer;

public interface DelivererRepository extends MongoRepository<Deliverer, String> {

}
