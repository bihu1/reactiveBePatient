package com.bihuniak.piotr.reactiveBePatient.domain.doctor;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public interface DoctorRepository extends ReactiveMongoRepository<Doctor, ObjectId> {
    Flux<Doctor> findAll();
}
