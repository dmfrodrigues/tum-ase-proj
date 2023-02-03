package com.asedelivery.backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.asedelivery.backend.model.Agent;
import com.asedelivery.backend.model.repo.AgentRepository;
import com.asedelivery.common.model.Role;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="Agent")
@RestController
@RequestMapping("/api/agent")
public class AgentController {

    @Autowired
    AgentRepository agentRepo;

    @Operation(summary="Get all agents")
    @GetMapping("")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public List<Agent> getAgent() {
        return agentRepo.findAll();
    }

    @Operation(summary="Get agent")
    @GetMapping("/{id}")
    @PreAuthorize(
        "hasRole('" + Role.DISPATCHER_STR + "') or " +
        "principal.username == #id"
    )
    public Agent getAgentByID(
        @PathVariable @Parameter(description="Agent ID") String id
    ) {
        return agentRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Operation(summary="Delete agent")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public void delAgent(
        @PathVariable @Parameter(description="Agent ID") String id
    ) {
        if (!agentRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else
            agentRepo.deleteById(id);
    }
}
