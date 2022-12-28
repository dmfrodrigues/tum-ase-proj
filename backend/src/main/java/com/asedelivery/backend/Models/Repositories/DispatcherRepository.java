package com.asedelivery.backend.Models.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asedelivery.backend.Models.Dispatcher;

public interface DispatcherRepository extends MongoRepository<Dispatcher, String> {

}
