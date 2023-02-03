package com.asedelivery.backend.model.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asedelivery.backend.model.Agent;

public interface AgentRepository extends MongoRepository<Agent, String> {
    public Optional<Agent> findByUsername(String username);
}
