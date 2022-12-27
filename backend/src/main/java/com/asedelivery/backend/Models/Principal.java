package com.asedelivery.backend.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class Principal {
    @Id
    private String id;

    private String password;

    public Principal(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }
}
