package com.bihuniak.piotr.reactiveBePatient.domain.profession;

import com.bihuniak.piotr.reactiveBePatient.domain.profession.http.model.ProfessionDetails;
import com.bihuniak.piotr.reactiveBePatient.domain.profession.http.model.ProfessionUpdate;
import com.bihuniak.piotr.reactiveBePatient.domain.profession.http.model.ProfessionView;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProfessionService {

    private final ProfessionRepository professionRepository;
    private final MapperFacade mapper;

    public Mono<ProfessionView> getProfession(String id){
        return professionRepository.findById(new ObjectId(id))
                .map(p ->  mapper.map(p, ProfessionView.class))
                .switchIfEmpty(Mono.error(RuntimeException::new));
    }

    public Flux<ProfessionView> getAllProfessions(){
        return professionRepository.findAll()
                .map(p -> mapper.map(p, ProfessionView.class));
    }

    public Mono<Void> addProfession(ProfessionDetails professionDetails){
        return Mono.just(mapper.map(professionDetails, Profession.class))
                .flatMap(professionRepository::save)
                .then();
    }

    public Mono<Void> updateProfession(String professionId, ProfessionUpdate professionUpdate){
        return Mono.just(professionId)
                .map(ObjectId::new)
                .flatMap(id -> Mono.just(id)
                                .filterWhen(professionRepository::existsById)
                                .switchIfEmpty(Mono.error(RuntimeException::new))
                )
                .map(x -> {
                    Profession map = mapper.map(professionUpdate, Profession.class);
                    map.setId(x);
                    return map;
                })
                .flatMap(professionRepository::save)
                .then();
    }

    public Mono<Void> deleteProfession(String professionId){
        return Mono.just(professionId)
                .map(ObjectId::new)
                .flatMap(id -> Mono.just(id)
                            .filterWhen(professionRepository::existsById)
                            .switchIfEmpty(Mono.error(RuntimeException::new))
                )
                .flatMap(professionRepository::deleteById)
                .then();
    }
}
