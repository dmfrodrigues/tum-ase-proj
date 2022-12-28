package com.asedelivery.backend.Models.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asedelivery.backend.Models.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

}
