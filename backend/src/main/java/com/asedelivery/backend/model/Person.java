package com.asedelivery.backend.model;

public abstract class Person extends Agent {
    public String name;
    public String email;

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
