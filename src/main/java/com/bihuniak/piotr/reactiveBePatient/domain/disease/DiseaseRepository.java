package com.bihuniak.piotr.reactiveBePatient.domain.disease;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface DiseaseRepository extends ReactiveMongoRepository<Disease, ObjectId> {
    Flux<Disease> findAll();
    Flux<Disease> findByIdIn(List<Long> idList);
}
