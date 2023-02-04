package com.asedelivery.backend.model.repo;

import java.util.Optional;

import com.asedelivery.backend.model.Dispatcher;

public interface DispatcherRepository extends MyMongoRepository<Dispatcher, String> {

    Optional<Dispatcher> findByUsername(String string);

}
