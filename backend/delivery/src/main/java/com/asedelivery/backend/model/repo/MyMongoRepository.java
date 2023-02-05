package com.asedelivery.backend.model.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface MyMongoRepository<T, ID> extends MongoRepository<T, ID> {
    @Query("{ '_class' : :#{#class} }")
    <S extends T> List<T> findByClass(@Param("class") String className);

    @Query("{ '_id': :#{#id}, '_class': :#{#class} }")
    <S extends T> Optional<T> findByIdAndClass(@Param("id") String id, @Param("class") String className);
}
