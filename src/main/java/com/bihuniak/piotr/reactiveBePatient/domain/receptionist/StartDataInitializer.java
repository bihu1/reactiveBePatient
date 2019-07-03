package com.bihuniak.piotr.reactiveBePatient.domain.receptionist;

import com.dryPepperoniStickTeam.bePatient.domain.disease.Disease;
import com.dryPepperoniStickTeam.bePatient.domain.disease.DiseaseRepository;
import com.dryPepperoniStickTeam.bePatient.domain.user.RoleRepository;
import com.dryPepperoniStickTeam.bePatient.domain.user.UserRepository;
import com.dryPepperoniStickTeam.bePatient.domain.user.model.UserRole;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.util.Arrays.asList;
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
        if(roleRepository.findAll().size() == 0){
            roleRepository.saveAll(asList(
                    new UserRole(0, "ROLE_ADMIN"),
                    new UserRole(0, "ROLE_DOCTOR"),
                    new UserRole(0, "ROLE_PATIENT"))
            );
        }
    }

    @SneakyThrows
    @SuppressWarnings(value = "unchecked")
    private void diseaseInitializer(){
        if(diseaseRepository.findAll().size() == 0){
//            File file = new File(ofNullable(getClass()
//                                .getClassLoader()
//                                .getResource("fileName")
//                            ).orElseThrow(() -> new RuntimeException("Nie ma takiego pliku śmieciu!"))
//                        .getFile()
//            );
            InputStream inputStream = new ClassPathResource("./ICD_10.csv").getInputStream();
            Reader reader = new InputStreamReader(inputStream , StandardCharsets.UTF_8);
            List<Disease> diseases = new CsvToBeanBuilder(reader)
                    .withType(Disease.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();
            diseaseRepository.saveAll(diseases);
        }
    }

    @Bean
    public void initializeFirstReceptionist(){
        roleInitializer();
        diseaseInitializer();
        Receptionist receptionist = new Receptionist(
                0,
                "Basia",
                "xvcf123",
                singletonList(roleRepository.findByRole("ROLE_ADMIN"))
        );
        if(!userRepository.existsByUsername("Basia")){
            userRepository.save(receptionist);
        }
    }
}
