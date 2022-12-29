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

import com.asedelivery.backend.Models.Deliverer;
import com.asedelivery.backend.Models.Principal;
import com.asedelivery.backend.Models.Repositories.DelivererRepository;
import com.asedelivery.backend.Models.Repositories.PrincipalRepository;

@RestController
@RequestMapping("/deliverer")
public class DelivererController {

    @Autowired
    DelivererRepository delivererRepo;

    @Autowired
    PrincipalRepository principalRepo;

    @GetMapping("")
    public List<Deliverer> getDeliverer() {
        return delivererRepo.findAll();
    }

    @GetMapping("/{id}")
    public Deliverer getDelivererByID(@PathVariable String id) {
        return delivererRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("")
    public Deliverer putDeliverer(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "email") String email) {
        Deliverer ret = delivererRepo.save(new Deliverer(username, name, email));
        principalRepo.save(new Principal(ret.getId(), username, password));
        return ret;
    }

    @DeleteMapping("/{id}")
    public void delDeliverer(@PathVariable String id) {
        if (!delivererRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else
            delivererRepo.deleteById(id);
    }
}