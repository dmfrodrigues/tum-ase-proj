package com.asedelivery.backend.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.asedelivery.backend.model.Dispatcher;
import com.asedelivery.backend.model.Principal;
import com.asedelivery.backend.model.repo.DispatcherRepository;
import com.asedelivery.backend.model.repo.PrincipalRepository;

@Component
public class SeederAdmin implements CommandLineRunner {
    @Autowired
    DispatcherRepository dispatcherRepo;

    @Autowired
    PrincipalRepository principalRepo;

    // @Autowired
    // BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {
        seed();
    }

    private void seed() {
        if(principalRepo.findByUsername("admin").isPresent()) return;

        String password = System.getenv("ADMIN_PASSWORD");
        Assert.notNull(password, "Admin password is null");

        Dispatcher admin = dispatcherRepo.save(new Dispatcher("admin", "Admin", "asedelivery@gmail.com"));
        principalRepo.save(new Principal(admin.getId(), admin.getRole(), admin.getUsername(), password));
        // principalRepo.save(new Principal(admin.getId(), admin.getRole(), admin.getUsername(), bCryptPasswordEncoder.encode(password)));

        System.out.println("DB seeded with admin");
    }
}
