package com.bihuniak.piotr.reactiveBePatient.domain.visit;

import com.bihuniak.piotr.reactiveBePatient.domain.disease.Disease;
import com.bihuniak.piotr.reactiveBePatient.domain.disease.DiseaseRepository;
import com.bihuniak.piotr.reactiveBePatient.domain.doctor.Doctor;
import com.bihuniak.piotr.reactiveBePatient.domain.doctor.DoctorRepository;
import com.bihuniak.piotr.reactiveBePatient.domain.patient.PatientRepository;
import com.bihuniak.piotr.reactiveBePatient.domain.patient.model.Patient;
import com.bihuniak.piotr.reactiveBePatient.domain.service.MedicalService;
import com.bihuniak.piotr.reactiveBePatient.domain.service.MedicalServiceRepository;
import com.bihuniak.piotr.reactiveBePatient.domain.visit.http.model.*;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.bihuniak.piotr.reactiveBePatient.domain.visit.FilterVisitStatus.AVAILABLE;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VisitService {

    private static DecimalFormat df = new DecimalFormat("0.00");

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    private final MedicalServiceRepository medicalServiceRepository;
    private final DiseaseRepository diseaseRepository;

    private final VisitRepository visitRepository;
    private final MapperFacade mapper;
    //private final TemplateEngine templateEngine;

    public Mono<Void> addDoctorAvailableVisit(String doctorId, VisitDetails visitDetails){
        return Mono.just(mapper.map(visitDetails, Visit.class))
            .doOnNext(v -> v.setStatus(VisitStatus.AVAILABLE))
            .doOnNext(v -> makeCascade2(v, doctorId))
            .flatMap(visitRepository::save)
            .then();
    }

    private void makeCascade2(Visit visit, String doctorId){
        doctorRepository.findById(new ObjectId(doctorId))
            .doOnNext(p -> p.getVisits().add(visit))
            .doOnNext(visit::setDoctor)
            .flatMap(doctorRepository::save);
    }

    public Flux<VisitView> getAllDoctorsVisitsByStatus(String doctorId, Optional<LocalDate> date, FilterVisitStatus patternVisitStatus){
        return doctorRepository.findById(new ObjectId(doctorId))
            .flatMapMany(d -> patternVisitStatus == AVAILABLE ? visitRepository.findByDoctorAndPatient(d, null) : visitRepository.findByDoctor(d))
            .filter(v ->
                    date.map(t -> v.getDateFrom().toLocalDate().equals(t))
                        .orElse(true)
            )
            .map(v -> mapper.map(v, VisitView.class));
    }

    public Flux<VisitView> getAllVisits(){
        return visitRepository.findAll()
                .map(v -> mapper.map(v, VisitView.class));
    }

    public Flux<ReservedVisitView> getAllPatientsVisits(String patientId){
        return patientRepository.findById(new ObjectId(patientId))
            .flatMapMany(visitRepository::findByPatient)
            .map(v -> mapper.map(v, ReservedVisitView.class));
    }

    public Mono<Void> reserveVisitByPatient(String doctorId, String visitId, String patientId){
        return doctorRepository.findById(new ObjectId(doctorId))
            .flatMapMany(d -> visitRepository.findByIdAndDoctor(new ObjectId(visitId), d))
            .doOnNext(v -> v.setStatus(VisitStatus.RESERVED))
            .doOnNext(v -> makeCascade(v, patientId))
            .flatMap(visitRepository::save)
            .then();
    }

    private void makeCascade(Visit visit, String patientId){
            patientRepository.findById(new ObjectId(patientId))
                .doOnNext(p -> p.getVisits().add(visit))
                .doOnNext(visit::setPatient)
                .flatMap(patientRepository::save);
    }

    public Mono<Void> assignDiseaseAndMedicalServices(String visitId , PatientVisitCard patientVisitCard){
        return visitRepository.findById(new ObjectId(visitId))
            .doOnNext(v -> mapper.map(patientVisitCard, v))
            .flatMap(v -> Mono.just(v)
                .filterWhen(z -> diseaseRepository.findByIdIn(patientVisitCard.getDiseases())
                    .doOnNext(d -> v.getDiseases().add(d))
                    .count()
                    .filter(x -> x != patientVisitCard.getDiseases().size())
                    .hasElement()
                )
                .switchIfEmpty(Mono.error(new RuntimeException()))
            )
            .flatMap(v -> Mono.just(v)
                .filterWhen(z -> medicalServiceRepository.findByIdIn(patientVisitCard.getServices())
                    .doOnNext(m -> v.getMedicalServices().add(m))
                    .count()
                    .filter(x -> x != patientVisitCard.getDiseases().size())
                    .hasElement()
                )
                .switchIfEmpty(Mono.error(new RuntimeException()))
            ).flatMap(visitRepository::save)
            .then();
    }

    public Mono<Void> changeVisitStatus(String visitId, VisitStatus status){
        return visitRepository.findById(new ObjectId(visitId))
                .doOnNext(v -> v.setStatus(status))
                .then();
    }

    public String generateHTMLView(String visitId){
//        Visit visit = visitRepository.findById(visitId).orElseThrow(RuntimeException::new);
//        Patient patient = visit.getPatient();
//        Context context = new Context();
//        context.setVariable("firstName", patient.getFirstName());
//        context.setVariable("lastName", patient.getLastName());
//        context.setVariable("email", patient.getEmail());
//        context.setVariable("pesel", patient.getUsername());
//        context.setVariable("phone", patient.getPhone());
//        context.setVariable("diseases", visit.getDiseases().stream().map(Disease::getName).collect(Collectors.joining( ", " )));
//        context.setVariable("mainSymptoms", visit.getMainSymptoms());
//        context.setVariable("treatment", visit.getTreatment());
//        context.setVariable("allergy", visit.getAllergy());
//        context.setVariable("addiction", visit.getAddiction());
//        context.setVariable("comment", visit.getComment());
//        context.setVariable("services", visit.getMedicalServices().stream().map(MedicalService::getService).collect(toList()));
//        context.setVariable("price", df.format(visit.getMedicalServices().stream().mapToDouble(MedicalService::getPrice).sum()));
//        return templateEngine.process("formula", context);
        return "";
    }
}
