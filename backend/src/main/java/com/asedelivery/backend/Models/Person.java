package com.asedelivery.backend.Models;

public class Person extends Agent {
    private String name;
    private String email;

    public Person(String username, String name, String email) {
        super(username);
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
