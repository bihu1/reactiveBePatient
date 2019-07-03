package com.bihuniak.piotr.reactiveBePatient.domain.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalServiceRepository extends CrudRepository<MedicalService, Long> {
    List<MedicalService> findAll();
    List<MedicalService> findByIdIn(List<Long> idList);
}
