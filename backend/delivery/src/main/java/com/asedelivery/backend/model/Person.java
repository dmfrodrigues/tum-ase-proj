package com.asedelivery.backend.model;

import org.springframework.data.mongodb.core.index.Indexed;

public abstract class Person extends Agent {
    public String name;

    @Indexed(unique = true)
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
