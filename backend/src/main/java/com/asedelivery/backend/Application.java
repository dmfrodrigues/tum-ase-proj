package com.asedelivery.backend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asedelivery.backend.Models.User;
import com.asedelivery.backend.Models.UserRepository;

@SpringBootApplication
@RestController
public class Application {

    @Autowired
    UserRepository userRepo;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/hello")
    public String hello(
            @RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/put")
    public User put(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "email") String email) {
        return userRepo.save(new User(name, password, email));
    }

    @GetMapping("/get")
    public List<User> get() {
        return userRepo.findAll();
    }
}
