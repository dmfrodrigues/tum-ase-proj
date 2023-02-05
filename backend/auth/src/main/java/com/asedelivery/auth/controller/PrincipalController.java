package com.asedelivery.auth.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.asedelivery.auth.model.Principal;
import com.asedelivery.auth.model.Token;
import com.asedelivery.auth.model.repo.PrincipalRepository;
import com.asedelivery.auth.model.repo.TokenRepository;
import com.asedelivery.common.model.Role;

@RestController
@RequestMapping("/api/auth/principal")
public class PrincipalController {

    @Autowired
    PrincipalRepository principalRepo;

    @Autowired
    TokenRepository tokenRepo;

    @GetMapping("")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public List<Principal> getPrincipals() {
        return principalRepo.findAll();
    }

    @GetMapping("/{id}")
    @PostAuthorize(
        "hasRole('" + Role.DISPATCHER_STR + "') or " +
        "principal == returnObject.principal.getId()"
    )
    public Principal getPrincipal(@PathVariable String id) {
        return principalRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @PostAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public Principal putPrincipal(
        @PathVariable String id,
        @RequestParam(value = "username") String username,
        @RequestParam(value = "password") String password,
        @RequestParam(value = "role") Role role
    ){
        Principal principal = principalRepo.save(new Principal(
            id,
            role,
            username,
            password
        ));
        
        return principal;
    }

    @PatchMapping("/{id}")
    @PostAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public Principal patchPrincipal(
        @PathVariable String id,
        @RequestParam(value = "password") Optional<String> password,
        @RequestParam(value = "role") Optional<Role> role
    ){
        Principal principal = principalRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(password.isPresent()) principal.password = password.get();
        if(role    .isPresent()) principal.role     = role    .get();

        principal = principalRepo.save(principal);
        
        return principal;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public void delPrincipal(@PathVariable String id) {
        Principal principal = principalRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        List<Token> tokens = tokenRepo.findByPrincipal(principal);
        for(Token token: tokens)
            token.principal = null;
        tokens = tokenRepo.saveAll(tokens);

        principalRepo.deleteById(id);
    }

}
