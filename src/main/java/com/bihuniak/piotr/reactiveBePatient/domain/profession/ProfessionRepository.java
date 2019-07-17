package com.bihuniak.piotr.reactiveBePatient.domain.profession;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface ProfessionRepository extends ReactiveMongoRepository<Profession, ObjectId> {
    Flux<Profession> findAll();
    Flux<Profession> findByIdIn(List<String> idList);
}
