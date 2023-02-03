package com.asedelivery.backend.model.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asedelivery.backend.model.Box;

public interface BoxRepository extends MongoRepository<Box, String> {

}
