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

import com.asedelivery.backend.Models.Dispatcher;
import com.asedelivery.backend.Models.DispatcherRepository;

@RestController
@RequestMapping("/dispatcher")
public class DispatcherController {

    @Autowired
    DispatcherRepository dispatcherRepo;

    @GetMapping("")
    public List<Dispatcher> getDispatcher() {
        return dispatcherRepo.findAll();
    }

    @GetMapping("/{id}")
    public Dispatcher getDispatcherByID(@PathVariable String id) {
        return dispatcherRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("")
    public Dispatcher putDispatcher(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "email") String email) {
        return dispatcherRepo.save(new Dispatcher(name, password, email));
    }

    @DeleteMapping("/{id}")
    public void delDispatcher(@PathVariable String id) {
        dispatcherRepo.deleteById(id);
    }
}
