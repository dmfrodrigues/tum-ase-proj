package com.asedelivery.backend.Models.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asedelivery.backend.Models.Box;

public interface BoxRepository extends MongoRepository<Box, String> {

}
