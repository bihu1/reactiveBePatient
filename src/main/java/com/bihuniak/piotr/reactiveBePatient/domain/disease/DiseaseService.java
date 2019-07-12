package com.bihuniak.piotr.reactiveBePatient.domain.disease;

import com.bihuniak.piotr.reactiveBePatient.domain.disease.http.model.DiseaseDetails;
import com.bihuniak.piotr.reactiveBePatient.domain.disease.http.model.DiseaseUpdate;
import com.bihuniak.piotr.reactiveBePatient.domain.disease.http.model.DiseaseView;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;
    private final MapperFacade mapper;

    public Mono<DiseaseView> getDisease(String id){
        return diseaseRepository.findById(new ObjectId(id))
                .map(d -> mapper.map(d, DiseaseView.class));
    }

    public Flux<DiseaseView> getAllDiseases(){
        return diseaseRepository.findAll()
                .map(d -> mapper.map(d, DiseaseView.class));
    }

    public Mono<Void> addDisease(DiseaseDetails diseaseDetails){
        return diseaseRepository.save(mapper.map(diseaseDetails, Disease.class))
                .then();
    }

    public Mono<Void> updateDisease(String id, DiseaseUpdate diseaseUpdate){
        return diseaseRepository.findById(new ObjectId(id))
                .flatMap(existingDisease -> {
                    mapper.map(diseaseUpdate, existingDisease);
                    return diseaseRepository.save(existingDisease).then();
                });
    }

    public Mono<Void> deleteDisease(String id){
        return diseaseRepository.findById(new ObjectId(id))
                .flatMap(existingDisease -> diseaseRepository.delete(existingDisease).then());
    }
}
