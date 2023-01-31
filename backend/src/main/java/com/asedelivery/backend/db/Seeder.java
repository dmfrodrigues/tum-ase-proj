package com.asedelivery.backend.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.asedelivery.backend.model.Box;
import com.asedelivery.backend.model.Customer;
import com.asedelivery.backend.model.Deliverer;
import com.asedelivery.backend.model.Delivery;
import com.asedelivery.backend.model.Dispatcher;
import com.asedelivery.backend.model.Principal;
import com.asedelivery.backend.model.repo.BoxRepository;
import com.asedelivery.backend.model.repo.CustomerRepository;
import com.asedelivery.backend.model.repo.DelivererRepository;
import com.asedelivery.backend.model.repo.DeliveryRepository;
import com.asedelivery.backend.model.repo.DispatcherRepository;
import com.asedelivery.backend.model.repo.PrincipalRepository;

@Component
@Order(1)
public class Seeder implements CommandLineRunner {
    @Autowired
    DispatcherRepository dispatcherRepo;

    @Autowired
    DelivererRepository delivererRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    BoxRepository boxRepo;

    @Autowired
    DeliveryRepository deliveryRepo;

    @Autowired
    PrincipalRepository principalRepo;

    @Override
    public void run(String... args) throws Exception {
        seed();
    }

    private void seed() {
        if(deliveryRepo.count() != 0) return;

        Dispatcher dispatcher = dispatcherRepo.save(new Dispatcher("dmfr", "Diogo Rodrigues", "dmfr@gmail.com"));
        principalRepo.save(new Principal(dispatcher.getId(), dispatcher.getRole(), dispatcher.getUsername(), "1234"));

        Deliverer deliverer1 = delivererRepo.save(new Deliverer("lucas", "João Lucas Martins", "lucas@gmail.com"));
        principalRepo.save(new Principal(deliverer1.getId(), deliverer1.getRole(), deliverer1.getUsername(), "5678"));

        Deliverer deliverer2 = delivererRepo.save(new Deliverer("martin", "Martin Mõhle", "martin@gmail.com"));
        principalRepo.save(new Principal(deliverer2.getId(), deliverer2.getRole(), deliverer2.getUsername(), "5678"));

        Customer customer = customerRepo.save(new Customer("mocho", "Tiago Silva", "mocho@gmail.com"));
        principalRepo.save(new Principal(customer.getId(), customer.getRole(), customer.getUsername(), "9012"));

        Box box = boxRepo.save(new Box("garching1", "Boltzmannstr. 3\n85748 Garching bei München"));
        principalRepo.save(new Principal(box.getId(), box.getRole(), box.getUsername(), "3456"));

        Delivery delivery = deliveryRepo.save(new Delivery(customer, dispatcher, deliverer1, "Lichtenbergstr. 6\n85748 Garching bei München", box));

        System.out.println("DB seeded with sample data");
    }
}
