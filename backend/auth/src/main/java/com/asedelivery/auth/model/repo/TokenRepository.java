package com.asedelivery.auth.model.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asedelivery.auth.model.Principal;
import com.asedelivery.auth.model.Token;

public interface TokenRepository extends MongoRepository<Token, String> {

    List<Token> findByPrincipal(Principal principal);

}
