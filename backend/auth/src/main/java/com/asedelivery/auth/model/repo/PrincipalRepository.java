package com.asedelivery.auth.model.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asedelivery.auth.model.Principal;

public interface PrincipalRepository extends MongoRepository<Principal, String> {
    public Optional<Principal> findByUsername(String username);
}
