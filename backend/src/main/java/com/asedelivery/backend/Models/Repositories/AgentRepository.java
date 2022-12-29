package com.asedelivery.backend.Models.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asedelivery.backend.Models.Agent;

public interface AgentRepository extends MongoRepository<Agent, String> {

}
