package com.bihuniak.piotr.reactiveBePatient.domain.doctor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {
    List<Doctor> findAll();
}
