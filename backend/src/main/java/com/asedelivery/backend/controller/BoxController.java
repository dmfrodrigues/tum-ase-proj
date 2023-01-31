package com.asedelivery.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.asedelivery.backend.model.Box;
import com.asedelivery.backend.model.Principal;
import com.asedelivery.backend.model.repo.BoxRepository;
import com.asedelivery.backend.model.repo.PrincipalRepository;

@RestController
@RequestMapping("/box")
public class BoxController {
    @Autowired
    BoxRepository boxRepo;

    @Autowired
    PrincipalRepository principalRepo;

    @GetMapping("")
    @PreAuthorize("hasRole('" + Principal.Role.DISPATCHER_STR + "')")
    public List<Box> getBoxes() {
        return boxRepo.findAll();
    }

    @PutMapping("")
    @PreAuthorize("hasRole('" + Principal.Role.DISPATCHER_STR + "')")
    public Box putBox(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "address") String address) {
        Box ret = boxRepo.save(new Box(username, address));
        principalRepo.save(new Principal(ret.getId(), ret.getRole(), username, password));
        return ret;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('" + Principal.Role.DISPATCHER_STR + "')")
    public void delCustomer(@PathVariable String id) {
        if (!boxRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else
            boxRepo.deleteById(id);
    }
}
