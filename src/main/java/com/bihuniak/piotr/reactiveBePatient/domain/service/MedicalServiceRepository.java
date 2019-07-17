package com.bihuniak.piotr.reactiveBePatient.domain.service;

import com.bihuniak.piotr.reactiveBePatient.domain.profession.Profession;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface MedicalServiceRepository extends ReactiveMongoRepository<MedicalService, ObjectId> {

    Flux<MedicalService> findByIdIn(List<String> idList);
}
