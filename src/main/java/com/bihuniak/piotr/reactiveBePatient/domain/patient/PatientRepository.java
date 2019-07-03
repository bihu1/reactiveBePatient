package com.bihuniak.piotr.reactiveBePatient.domain.patient;

import com.dryPepperoniStickTeam.bePatient.domain.patient.model.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {

    boolean existsByUsername(String username);
    List<Patient> findAll();
}
