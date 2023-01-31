package com.asedelivery.backend.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("principal")
public class Principal {
    public enum Role {
        DISPATCHER,
        DELIVERER,
        CUSTOMER,
        BOX;

        public static final String DISPATCHER_STR = "DISPATCHER";
        public static final String DELIVERER_STR = "DELIVERER";
        public static final String CUSTOMER_STR = "CUSTOMER";
        public static final String BOX_STR = "BOX";

        public String toString(){
            switch(this){
                case DISPATCHER : return DISPATCHER_STR;
                case DELIVERER  : return DELIVERER_STR;
                case CUSTOMER   : return CUSTOMER_STR;
                case BOX        : return BOX_STR;
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
