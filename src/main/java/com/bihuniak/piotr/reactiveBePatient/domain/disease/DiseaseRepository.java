package com.bihuniak.piotr.reactiveBePatient.domain.disease;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRepository extends CrudRepository<Disease, Long> {
    List<Disease> findAll();
    List<Disease> findByIdIn(List<Long> idList);
}
