package com.asedelivery.backend.Models.Repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asedelivery.backend.Models.Agent;

public interface AgentRepository extends MongoRepository<Agent, String> {
    public Optional<Agent> findByUsername(String username);
}
