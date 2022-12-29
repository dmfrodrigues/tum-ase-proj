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
import com.asedelivery.backend.Models.Principal;
import com.asedelivery.backend.Models.Repositories.DispatcherRepository;
import com.asedelivery.backend.Models.Repositories.PrincipalRepository;

@RestController
@RequestMapping("/dispatcher")
public class DispatcherController {

    @Autowired
    DispatcherRepository dispatcherRepo;

    @Autowired
    PrincipalRepository principalRepo;

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
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "email") String email) {
        Dispatcher ret = dispatcherRepo.save(new Dispatcher(username, name, email));
        principalRepo.save(new Principal(ret.getId(), username, password));
        return ret;
    }

    @DeleteMapping("/{id}")
    public void delDispatcher(@PathVariable String id) {
        if (!dispatcherRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else
            dispatcherRepo.deleteById(id);
    }
}
