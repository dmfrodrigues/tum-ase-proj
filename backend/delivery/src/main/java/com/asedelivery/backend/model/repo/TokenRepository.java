package com.asedelivery.backend.model.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asedelivery.backend.model.Token;

public interface TokenRepository extends MongoRepository<Token, String> {

}
