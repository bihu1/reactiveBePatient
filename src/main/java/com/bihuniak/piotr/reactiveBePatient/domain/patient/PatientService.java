package com.bihuniak.piotr.reactiveBePatient.domain.patient;

import com.bihuniak.piotr.reactiveBePatient.common.mail.MailService;
import com.bihuniak.piotr.reactiveBePatient.domain.patient.http.model.PatientDetails;
import com.bihuniak.piotr.reactiveBePatient.domain.patient.http.model.PatientView;
import com.bihuniak.piotr.reactiveBePatient.domain.patient.model.Patient;
import com.bihuniak.piotr.reactiveBePatient.domain.user.RoleRepository;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PatientService {

    private final PatientRepository patientRepository;
    private final RoleRepository roleRepository;
    private final MailService mailService;
    private final MapperFacade mapper;

    public Flux<PatientView> getAllPatients(){
        return patientRepository.findAll()
                .map(p -> mapper.map(p, PatientView.class));
    }

    public Mono<PatientView> getPatient(String patientId){
        return patientRepository.findById(new ObjectId(patientId))
                .map(p -> mapper.map(p, PatientView.class))
                .switchIfEmpty(Mono.error(RuntimeException::new));
    }

    public Mono<Void> register(PatientDetails patientDetails){
        return Mono.just(mapper.map(patientDetails, Patient.class))
                .doOnNext(p -> p.setRoles(singletonList(roleRepository.findByRole("ROLE_PATIENT"))))
                .flatMap(p -> Mono.just(p)
                                .filterWhen(x -> patientRepository.existsByUsername(x.getUsername()))
                                .switchIfEmpty(Mono.error(RuntimeException::new))
                )
                .flatMap(patientRepository::save)
                .doOnNext(p -> mailService.sendSimpleMessage(p.getEmail(),"Rejestracja w bePatient",
                        "Gratulujemy udało Ci się pomyślnie zarejestrować, teraz możesz w pełni korzystać z naszego systemu \n bePatient Admin")
                )
                .then();
    }

    public Mono<Void> sendMailToReception(String patientId, String message){
        mailService.sendSimpleMessage("bepatientclinic@gmail.com","Wiadomość od użytkownika","Uzytkownik"+patientId+" przesyla wiadomosc"+message);
        return Mono.empty();
    }
}
