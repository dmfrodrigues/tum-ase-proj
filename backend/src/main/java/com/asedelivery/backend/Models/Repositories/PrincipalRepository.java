package com.asedelivery.backend.Models.Repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asedelivery.backend.Models.Principal;

public interface PrincipalRepository extends MongoRepository<Principal, String> {
    public Optional<Principal> findByUsername(String username);
}
