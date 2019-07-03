package com.bihuniak.piotr.reactiveBePatient.domain.visit;

import com.dryPepperoniStickTeam.bePatient.domain.doctor.Doctor;
import com.dryPepperoniStickTeam.bePatient.domain.patient.model.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitRepository extends CrudRepository<Visit, Long> {
    List<Visit> findByDoctorAndPatient(Doctor doctor, Patient patient);
    List<Visit> findByPatient(Patient patient);
    List<Visit> findByDoctor(Doctor doctor);
    List<Visit> findAll();
    Optional<Visit> findByIdAndDoctor(long id, Doctor doctor);
}
