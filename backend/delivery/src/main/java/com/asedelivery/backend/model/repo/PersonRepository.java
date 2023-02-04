package com.asedelivery.backend.model.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asedelivery.backend.model.Person;

public interface PersonRepository extends MongoRepository<Person, String> {

}
