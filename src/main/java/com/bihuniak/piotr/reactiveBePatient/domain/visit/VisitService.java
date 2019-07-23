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
import static java.util.stream.Collectors.toList;

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
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(RuntimeException::new);
        Visit visit = mapper.map(visitDetails, Visit.class);
        visit.setStatus(VisitStatus.AVAILABLE);
        visit.setDoctor(doctor);
        doctor.addVisit(visit);
        visitRepository.save(visit);
    }

    public Flux<VisitView> getAllDoctorsVisitsByStatus(String doctorId, Optional<LocalDate> date, FilterVisitStatus patternVisitStatus){
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(RuntimeException::new);
        List<Visit> visits = (patternVisitStatus == AVAILABLE ? visitRepository.findByDoctorAndPatient(doctor, null) : visitRepository.findByDoctor(doctor))
                .stream()
                .filter(v ->
                        date.map(d -> v.getDateFrom()
                                .toLocalDate()
                                .equals(d)
                        )
                        .orElse(true)
                )
                .collect(toList());
        return mapper.mapAsList(visits, VisitView.class);
    }

    public Flux<VisitView> getAllVisits(){
        return visitRepository.findAll()
                .map(v -> mapper.map(v, VisitView.class));
    }

    public Flux<ReservedVisitView> getAllPatientsVisits(String patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow(RuntimeException::new);
        List<Visit> visits = visitRepository.findByPatient(patient);
        return mapper.mapAsList(visits, ReservedVisitView.class);
    }

    public Mono<Void> reserveVisitByPatient(String doctorId, String visitId, String patientId){
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(RuntimeException::new);
        Visit visit = visitRepository.findByIdAndDoctor(visitId, doctor).orElseThrow(RuntimeException::new);
        Patient patient = patientRepository.findById(patientId).orElseThrow(RuntimeException::new);
        visit.setPatient(patient);
        visit.setStatus(VisitStatus.RESERVED);
        patient.getVisits().add(visit);
    }

    public Mono<Void> assignDiseaseAndMedicalServices(String visitId , PatientVisitCard patientVisitCard){
        List<Disease> diseases = diseaseRepository.findByIdIn(patientVisitCard.getDiseases());
        if(diseases.size() != patientVisitCard.getDiseases().size()){
            throw new RuntimeException();
        }
        List<MedicalService> medicalServices = medicalServiceRepository.findByIdIn(patientVisitCard.getServices());
        if(medicalServices.size() != patientVisitCard.getServices().size()){
            throw new RuntimeException();
        }

        Visit visit = visitRepository.findById(visitId).orElseThrow(RuntimeException::new);
        visit.setDiseases(diseases);
        visit.setMedicalServices(medicalServices);
        visit.setMainSymptoms(patientVisitCard.getMainSymptoms());
        visit.setTreatment(patientVisitCard.getTreatment());
        visit.setAllergy(patientVisitCard.getAllergy());
        visit.setAddiction(patientVisitCard.getAddiction());
        visit.setComment(patientVisitCard.getComment());
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
