package com.asedelivery.backend.Models;

public class Person extends Principal {
    private String name;
    private String email;

    public Person(String name, String password, String email) {
        super(password);
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
