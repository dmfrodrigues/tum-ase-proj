package com.asedelivery.auth.model.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asedelivery.auth.model.Token;



public interface TokenRepository extends MongoRepository<Token, String> {

}
