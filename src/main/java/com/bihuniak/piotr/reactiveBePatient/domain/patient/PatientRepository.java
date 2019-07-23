package com.bihuniak.piotr.reactiveBePatient.domain.patient;

import com.bihuniak.piotr.reactiveBePatient.domain.patient.model.Patient;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PatientRepository extends ReactiveMongoRepository<Patient, ObjectId> {

    Mono<Boolean> existsByUsername(String username);
}
