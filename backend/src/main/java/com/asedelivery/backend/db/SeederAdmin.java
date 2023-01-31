package com.asedelivery.backend.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.asedelivery.backend.Models.Dispatcher;
import com.asedelivery.backend.Models.Principal;
import com.asedelivery.backend.Models.Repositories.DispatcherRepository;
import com.asedelivery.backend.Models.Repositories.PrincipalRepository;

@Component
public class SeederAdmin implements CommandLineRunner {
    @Autowired
    DispatcherRepository dispatcherRepo;

    @Autowired
    PrincipalRepository principalRepo;

    @Override
    public void run(String... args) throws Exception {
        seed();
    }

    private void seed() {
        seedAdmin();
    }

    private void seedAdmin() {
        String password = System.getenv("ADMIN_PASSWORD");
        Assert.notNull(password, "Admin password is null");

        Dispatcher admin = dispatcherRepo.save(new Dispatcher("admin", "Admin", "asedelivery@gmail.com"));
        principalRepo.save(new Principal(admin.getId(), admin.getRole(), admin.getUsername(), password));

        System.out.println("DB seeded with admin");
    }
}
