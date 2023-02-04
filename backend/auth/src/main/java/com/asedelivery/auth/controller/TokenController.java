package com.asedelivery.auth.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.asedelivery.auth.model.Token;
import com.asedelivery.auth.model.repo.PrincipalRepository;
import com.asedelivery.auth.model.repo.TokenRepository;
import com.asedelivery.common.model.Role;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    PrincipalRepository principalRepo;

    @Autowired
    TokenRepository tokenRepo;

    @GetMapping("")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public List<Token> getTokens() {
        return tokenRepo.findAll();
    }

    @GetMapping("/{id}")
    @PostAuthorize(
        "hasRole('" + Role.DISPATCHER_STR + "') or " +
        "principal == returnObject.principal.getId()"
    )
    public Token getToken(@PathVariable String id) {
        return tokenRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    @PostAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public Token patchDelivery(
        @PathVariable String id,
        @RequestParam(value = "principalId") String principalId
    ){
        Token tokenObj = tokenRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        tokenObj.principal = principalRepo.findById(principalId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Principal not found"));
        
        return tokenObj;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('" + Role.DISPATCHER_STR + "')")
    public void delAgent(@PathVariable String id) {
        if (!tokenRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else
        tokenRepo.deleteById(id);
    }
}
