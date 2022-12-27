package com.asedelivery.backend.Models;

import org.springframework.data.annotation.Id;

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
