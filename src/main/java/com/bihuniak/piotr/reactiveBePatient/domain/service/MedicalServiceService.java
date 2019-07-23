package com.bihuniak.piotr.reactiveBePatient.domain.service;

import com.bihuniak.piotr.reactiveBePatient.domain.profession.Profession;
import com.bihuniak.piotr.reactiveBePatient.domain.service.http.model.MedicalServiceDetails;
import com.bihuniak.piotr.reactiveBePatient.domain.service.http.model.MedicalServiceUpdate;
import com.bihuniak.piotr.reactiveBePatient.domain.service.http.model.MedicalServiceView;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MedicalServiceService {

    private final MedicalServiceRepository medicalServiceRepository;
    private final MapperFacade mapper;

    public Mono<MedicalServiceView> getMedicalService(String id){
        return medicalServiceRepository.findById(new ObjectId(id))
                .map(m -> mapper.map(m, MedicalServiceView.class));
    }

    public Flux<MedicalServiceView> getAllMedicalServices(){
        return medicalServiceRepository.findAll()
                .map(m -> mapper.map(m, MedicalServiceView.class));
    }

    public Mono<Void> addMedicalService(MedicalServiceDetails medicalServiceDetails){
        return Mono.just(mapper.map(medicalServiceDetails, MedicalService.class))
                .flatMap(medicalServiceRepository::save)
                .then();
    }

    public Mono<Void> updateMedicalService(String medicalServiceId, MedicalServiceUpdate medicalServiceUpdate){
        return Mono.just(medicalServiceId)
                .map(ObjectId::new)
                .flatMap(id -> Mono.just(id)
                        .filterWhen(medicalServiceRepository::existsById)
                        .switchIfEmpty(Mono.error(RuntimeException::new))
                )
                .map(x -> {
                    MedicalService map = mapper.map(medicalServiceUpdate, MedicalService.class);
                    map.setId(x);
                    return map;
                })
                .flatMap(medicalServiceRepository::save)
                .then();
    }

    public Mono<Void> deleteMedicalService(String medicalServiceId){
        return Mono.just(medicalServiceId)
                .map(ObjectId::new)
                .flatMap(id -> Mono.just(id)
                        .filterWhen(medicalServiceRepository::existsById)
                        .switchIfEmpty(Mono.error(RuntimeException::new))
                )
                .flatMap(medicalServiceRepository::deleteById)
                .then();
    }
}
