package com.asedelivery.backend.Models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Document("principal")
public class Principal {
    public enum Role {
        DISPATCHER,
        DELIVERER,
        CUSTOMER,
        BOX;

        public String toString(){
            switch(this){
                case DISPATCHER: return "DISPATCHER";
                case DELIVERER: return "DELIVERER";
                case CUSTOMER: return "CUSTOMER";
                case BOX: return "BOX";
            }
            return null;
        }
    }

    @Id
    private String id;

    private Role role;

    private String username;

    private String password;

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

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}
