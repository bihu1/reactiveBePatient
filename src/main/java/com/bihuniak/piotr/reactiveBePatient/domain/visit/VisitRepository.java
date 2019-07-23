package com.bihuniak.piotr.reactiveBePatient.domain.visit;

import com.bihuniak.piotr.reactiveBePatient.domain.doctor.Doctor;
import com.bihuniak.piotr.reactiveBePatient.domain.patient.model.Patient;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface VisitRepository extends ReactiveMongoRepository<Visit, ObjectId> {
    Flux<Visit> findByDoctorAndPatient(Doctor doctor, Patient patient);
    Flux<Visit> findByPatient(Patient patient);
    Flux<Visit> findByDoctor(Doctor doctor);
    Flux<Visit> findAll();
    Mono<Visit> findByIdAndDoctor(ObjectId id, Doctor doctor);
}
