package com.asedelivery.backend.model.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asedelivery.backend.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

}
