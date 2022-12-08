package com.asedelivery.backend.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.asedelivery.backend.Models.Customer;
import com.asedelivery.backend.Models.CustomerRepository;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepo;

    @GetMapping("")
    public List<Customer> getCustomer() {
        return customerRepo.findAll();
    }

    @GetMapping("/{id}")
    public Customer getCustomerByID(@PathVariable String id) {
        return customerRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("")
    public Customer putCustomer(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "email") String email) {
        return customerRepo.save(new Customer(name, password, email));
    }

    @DeleteMapping("/{id}")
    public void delCustomer(@PathVariable String id) {
        customerRepo.deleteById(id);
    }
}
