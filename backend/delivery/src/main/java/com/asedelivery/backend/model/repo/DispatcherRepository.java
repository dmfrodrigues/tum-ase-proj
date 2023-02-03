package com.asedelivery.backend.model.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asedelivery.backend.model.Dispatcher;

public interface DispatcherRepository extends MongoRepository<Dispatcher, String> {

}
