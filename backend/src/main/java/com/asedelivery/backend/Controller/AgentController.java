package com.asedelivery.backend.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.asedelivery.backend.Models.Agent;
import com.asedelivery.backend.Models.Repositories.AgentRepository;

@RestController
@RequestMapping("/agent")
public class AgentController {

    @Autowired
    AgentRepository agentRepo;

    @GetMapping("")
    public List<Agent> getAgent() {
        return agentRepo.findAll();
    }

    @GetMapping("/{id}")
    public Agent getAgentByID(@PathVariable String id) {
        return agentRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void delAgent(@PathVariable String id) {
        if (!agentRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else
            agentRepo.deleteById(id);
    }
}
