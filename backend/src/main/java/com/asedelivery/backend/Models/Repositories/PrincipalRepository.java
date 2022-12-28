package com.asedelivery.backend.Models.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asedelivery.backend.Models.Principal;

public interface PrincipalRepository extends MongoRepository<Principal, String> {

}
