package com.asedelivery.auth.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.asedelivery.common.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Document("principal")
public class Principal {

    @Id
    private String id;

    public Role role;

    @Indexed(unique = true)
    private String username;

    @JsonIgnore
    public String password;

    public Principal(String id, Role role, String username, String password) {
        this.id = id;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
