package com.bihuniak.piotr.reactiveBePatient.domain.receptionist;

import com.bihuniak.piotr.reactiveBePatient.domain.disease.Disease;
import com.bihuniak.piotr.reactiveBePatient.domain.disease.DiseaseRepository;
import com.bihuniak.piotr.reactiveBePatient.domain.user.RoleRepository;
import com.bihuniak.piotr.reactiveBePatient.domain.user.UserRepository;
import com.bihuniak.piotr.reactiveBePatient.domain.user.model.UserRole;
import com.opencsv.bean.CsvToBeanBuilder;
import org.bson.types.ObjectId;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.util.Collections.singletonList;

@Component
public class StartDataInitializer {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DiseaseRepository diseaseRepository;

    private void roleInitializer(){
        roleRepository.findAll()
                .count()
                .filter(x -> x == 0)
                .flatMapMany(x -> Flux.just(
                        new UserRole(ObjectId.get(), "ROLE_ADMIN"),
                        new UserRole(ObjectId.get(), "ROLE_DOCTOR"),
                        new UserRole(ObjectId.get(), "ROLE_PATIENT"))
                )
                .flatMap(roleRepository::save);
    }

    @SuppressWarnings(value = "unchecked")
    private void diseaseInitializer(){
         diseaseRepository.findAll().count()
                .filter(x -> x == 0)
                .map(x -> new ClassPathResource("./ICD_10.csv"))
                .map(Unchecked.function(ClassPathResource::getInputStream))
                .map(x -> new InputStreamReader(x , StandardCharsets.UTF_8))
                .flatMapMany(x -> Flux.fromIterable((List<Disease>)new CsvToBeanBuilder(x)
                        .withType(Disease.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build()
                        .parse())
                )
                .flatMap(diseaseRepository::save);
    }

    @Bean
    public void initializeFirstReceptionist(){
        roleInitializer();
        diseaseInitializer();
        Mono.just( new Receptionist(
                new ObjectId(),
                "Basia",
                "xvcf123",
                singletonList(roleRepository.findByRole("ROLE_ADMIN").block())
        ))
                .filterWhen(x -> userRepository.existsByUsername("Basia"))
                .flatMap(userRepository::save);
    }
}
