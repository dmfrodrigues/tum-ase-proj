package com.asedelivery.backend.Controller;

import java.security.NoSuchAlgorithmException;
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

import com.asedelivery.backend.Auth.Password;
import com.asedelivery.backend.Models.Box;
import com.asedelivery.backend.Models.Principal;
import com.asedelivery.backend.Models.Repositories.BoxRepository;
import com.asedelivery.backend.Models.Repositories.PrincipalRepository;

@RestController
@RequestMapping("/box")
public class BoxController {
    @Autowired
    BoxRepository boxRepo;

    @Autowired
    PrincipalRepository principalRepo;

    @GetMapping("")
    public List<Box> getBoxes() {
        return boxRepo.findAll();
    }

    @GetMapping("/{id}")
    public Box getBoxById(@PathVariable String id) {
        return boxRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("")
    public Box putBox(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "address") String address) {
        Box ret = boxRepo.save(new Box(username, address));
        Password passwordObj;
        try {
            passwordObj = new Password(password);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        principalRepo.save(new Principal(ret.getId(), username, passwordObj));
        return ret;
    }

    @DeleteMapping("/{id}")
    public void delCustomer(@PathVariable String id) {
        if (!boxRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else
            boxRepo.deleteById(id);
    }
}
