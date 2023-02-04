package com.asedelivery.backend.model.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface MyMongoRepository<T, ID> extends MongoRepository<T, ID> {
    @Query("{ '_class' : :#{#class} }")
    <S extends T> List<T> findByClass(@Param("class") String className);
}
