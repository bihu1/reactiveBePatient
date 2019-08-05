package com.bihuniak.piotr.reactiveBePatient.domain.doctor;

import com.bihuniak.piotr.reactiveBePatient.common.mail.MailService;
import com.bihuniak.piotr.reactiveBePatient.domain.doctor.http.model.DoctorDetails;
import com.bihuniak.piotr.reactiveBePatient.domain.doctor.http.model.DoctorUpdate;
import com.bihuniak.piotr.reactiveBePatient.domain.doctor.http.model.DoctorView;
import com.bihuniak.piotr.reactiveBePatient.domain.patient.model.Patient;
import com.bihuniak.piotr.reactiveBePatient.domain.profession.ProfessionRepository;
import com.bihuniak.piotr.reactiveBePatient.domain.service.MedicalServiceRepository;
import com.bihuniak.piotr.reactiveBePatient.domain.user.RoleRepository;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DoctorService {

    private static final String REGISTRATION_SUBJECT = "Rejestracja w bePatient";
    private final DoctorRepository doctorRepository;
    private final RoleRepository roleRepository;
    private final MedicalServiceRepository medicalServiceRepository;
    private final ProfessionRepository professionRepository;
    private final MailService mailService;
    private final MapperFacade mapper;

    public Flux<DoctorView> getAllDoctors(){
        return doctorRepository.findAll()
                .map(d -> mapper.map(d, DoctorView.class));
    }

    public Mono<DoctorView> getDoctor(String id){
        return doctorRepository.findById(new ObjectId(id))
                .map(d -> mapper.map(d, DoctorView.class));
    }

    public Mono<Void> addDoctor(DoctorDetails doctorDetails){
        String doctorPassword = UUID.randomUUID().toString();
        return Mono.just(doctorDetails)
                .map(o -> mapper.map(o, Doctor.class))
                .filterWhen(o -> setAndCheckProfessions(o, doctorDetails.getProfessions()))
                .switchIfEmpty(Mono.error(new RuntimeException("A chuj blad z proffesion")))
                .filterWhen(o -> setAndCheckMedicalServices(o, doctorDetails.getServices()))
                .switchIfEmpty(Mono.error(new RuntimeException("A chuj blad z medical")))
                .doOnNext(d -> d.setUsername(UUID.randomUUID().toString()))
                .doOnNext(d -> d.setPassword(doctorPassword))
                .doOnNext(d -> setRole(d))
                .flatMap(doctorRepository::save)
                .doOnNext(d -> mailService.sendSimpleMessage(
                        d.getEmail(),
                        REGISTRATION_SUBJECT,
                        generateMessageContent(d.getUsername(), doctorPassword))
                )
                .then();
    }

    private void setRole(Doctor doctor){
        roleRepository.findByRole("ROLE_DOCTOR")
            .doOnNext(x -> doctor.getRoles().add(x));
    }

    public Mono<Void> updateDoctor(String doctorId, DoctorUpdate doctorUpdate) {
            return doctorRepository.findById(new ObjectId(doctorId))
                .doOnNext(o -> mapper.map(doctorUpdate, o))
                .switchIfEmpty(Mono.error(new RuntimeException("Not exist")))
                .filterWhen(o -> setAndCheckProfessions(o, doctorUpdate.getProfessions()))
                .switchIfEmpty(Mono.error(new RuntimeException("A chuj blad z proffesion")))
                .filterWhen(o -> setAndCheckMedicalServices(o, doctorUpdate.getServices()))
                .switchIfEmpty(Mono.error(new RuntimeException("A chuj blad z medical")))
                .flatMap(doctorRepository::save)
                .then();
    }

    public Mono<Void> deleteDoctor(String doctorId){
        return Mono.just(new ObjectId(doctorId))
                .flatMap(id -> Mono.just(id)
                        .filterWhen(doctorRepository::existsById)
                        .switchIfEmpty(Mono.error(new RuntimeException("A chuj blad z medical")))
                )
                .flatMap(doctorRepository::deleteById);
    }

    private Mono<Boolean> setAndCheckProfessions(Doctor o, List<String> professions) {
        return setAndCheckFlux(professionRepository.findByIdIn(professions),
                o::setProfessions,
                x -> o.getProfessions().size() > x
        );
    }

    private Mono<Boolean> setAndCheckMedicalServices(Doctor o, List<String> services) {
        return setAndCheckFlux(medicalServiceRepository.findByIdIn(services),
                o::setMedicalServices,
                x -> o.getMedicalServices().size() > x
        );
    }

    private String generateMessageContent(String login, String password){
        return new StringBuilder()
                .append("Gratulujemy utworzono Ci konto w aplikacji bePatient Twój:")
                .append(System.lineSeparator())
                .append("login:")
                .append(login)
                .append(System.lineSeparator())
                .append("hasło")
                .append(password)
                .append(System.lineSeparator())
                .append("Login i hasło należy zmienić po pierwszym logowaniu.")
                .append(System.lineSeparator())
                .append("bePatient Admin")
                .toString();
    }

    private static <V> Mono<Boolean> setAndCheckFlux(Flux<V> flux, Consumer<List<V>> setter, Predicate<Long> predicate){
        return flux
                .collectList()
                .doOnNext(setter)
                .flatMapIterable(x -> x)
                .count()
                .filter(predicate)
                .map(x -> true);
    }
}
