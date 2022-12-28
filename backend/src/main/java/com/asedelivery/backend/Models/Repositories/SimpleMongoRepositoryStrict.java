package com.asedelivery.backend.Models.Repositories;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

public class SimpleMongoRepositoryStrict<T, ID> extends SimpleMongoRepository<T, ID> {

    public SimpleMongoRepositoryStrict(MongoEntityInformation<T, ID> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
    }

}
