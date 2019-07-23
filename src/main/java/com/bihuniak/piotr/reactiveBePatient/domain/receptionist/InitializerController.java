package com.bihuniak.piotr.reactiveBePatient.domain.receptionist;

import com.bihuniak.piotr.reactiveBePatient.domain.disease.Disease;
import com.bihuniak.piotr.reactiveBePatient.domain.disease.DiseaseRepository;
import com.bihuniak.piotr.reactiveBePatient.domain.doctor.Doctor;
import com.bihuniak.piotr.reactiveBePatient.domain.doctor.DoctorRepository;
import com.bihuniak.piotr.reactiveBePatient.domain.patient.PatientRepository;
import com.bihuniak.piotr.reactiveBePatient.domain.patient.model.Patient;
import com.bihuniak.piotr.reactiveBePatient.domain.profession.Profession;
import com.bihuniak.piotr.reactiveBePatient.domain.profession.ProfessionRepository;
import com.bihuniak.piotr.reactiveBePatient.domain.service.MedicalService;
import com.bihuniak.piotr.reactiveBePatient.domain.service.MedicalServiceRepository;
import com.bihuniak.piotr.reactiveBePatient.domain.user.RoleRepository;
import com.bihuniak.piotr.reactiveBePatient.domain.visit.Visit;
import com.bihuniak.piotr.reactiveBePatient.domain.visit.VisitRepository;
import com.bihuniak.piotr.reactiveBePatient.domain.visit.http.model.VisitStatus;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class InitializerController {

    ProfessionRepository professionRepository;
    DiseaseRepository diseaseRepository;
    MedicalServiceRepository medicalServiceRepository;
    VisitRepository visitRepository;
    DoctorRepository doctorRepository;
    PatientRepository patientRepository;
    RoleRepository roleRepository;
    
    @PostMapping("/initializing/test/data")
    @ApiOperation(value="Fill database for tests")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
    })
    @ResponseStatus(code = HttpStatus.OK)
    public void testDataInitiallizer(){
        Profession profession = new Profession(new ObjectId(), "Kardiolog");
        Profession profession1 = new Profession(new ObjectId(), "Nefrolog");
        Profession profession2 = new Profession(new ObjectId(), "Urolog");
        Profession profession3 = new Profession(new ObjectId(), "Dermatolog");
        Profession profession4 = new Profession(new ObjectId(), "Psychiatra");

        professionRepository.saveAll(asList(profession, profession1, profession2, profession3, profession4));

        MedicalService medicalService = new MedicalService(new ObjectId(), "Tomografia", 500);
        MedicalService medicalService1 = new MedicalService(new ObjectId(), "Rezonans Magnetyczny", 1000);
        MedicalService medicalService2 = new MedicalService(new ObjectId(), "Konsultacja dermatologiczna", 250);
        MedicalService medicalService3 = new MedicalService(new ObjectId(), "USG 4D", 400);
        MedicalService medicalService4 = new MedicalService(new ObjectId(), "Laseroterapia", 300);

        medicalServiceRepository.saveAll(asList(medicalService, medicalService1, medicalService2, medicalService3, medicalService4));

        Disease disease = new Disease(new ObjectId(), "Biegunka");
        Disease disease1 = new Disease(new ObjectId(), "Ostoporoza");
        Disease disease2 = new Disease(new ObjectId(), "Jaskra");
        Disease disease3 = new Disease(new ObjectId(), "Częstomocz");
        Disease disease4 = new Disease(new ObjectId(), "Stwardnienie rozsiane");

        diseaseRepository.saveAll(asList(disease, disease1, disease2, disease3, disease4));

        Patient patient = new Patient(new ObjectId(), "90011241122", "alaMakota1", singletonList(roleRepository.findByRole("ROLE_PATIENT").block()), "andrzej@gmail.com", null, "Jan", "Kozak", "123456789");
        Patient patient1 = new Patient(new ObjectId(), "91011241122", "alaMakota2", singletonList(roleRepository.findByRole("ROLE_PATIENT").block()), "andrzej@gmail.com", null, "Andrzej", "Lumpacz", "123456789");
        Patient patient2 = new Patient(new ObjectId(), "92011241122", "alaMakota3", singletonList(roleRepository.findByRole("ROLE_PATIENT").block()), "andrzej@gmail.com", null, "Beata", "Rozstowska", "123456789");
        Patient patient3 = new Patient(new ObjectId(), "93011241122", "alaMakota4", singletonList(roleRepository.findByRole("ROLE_PATIENT").block()), "andrzej@gmail.com", null, "Kamil", "Kominiok", "123456789");
        Patient patient4 = new Patient(new ObjectId(), "94011241122", "alaMakota5", singletonList(roleRepository.findByRole("ROLE_PATIENT").block()), "andrzej@gmail.com", null, "Sebastian", "Korzenny", "123456789");

        Doctor doctor = new Doctor(new ObjectId(), "doctorHouse", "kotMaAle", singletonList(roleRepository.findByRole("ROLE_DOCTOR").block()),
                "Aleksander", "Ziółko", "dr n. md.", "ziolko@gmail.com" , asList(profession, profession3), null, asList(medicalService, medicalService1));
        Doctor doctor1 = new Doctor(new ObjectId(), "missMedico", "kotMaAle1", singletonList(roleRepository.findByRole("ROLE_DOCTOR").block()),
                "Grzegorz", "Lecznicki", "dr n. md.", "grzegorz_lecznicki@gmail.com" , asList(profession, profession3), null, asList(medicalService, medicalService1));
        Doctor doctor2 = new Doctor(new ObjectId(), "drChoroba52", "kotMaAle", singletonList(roleRepository.findByRole("ROLE_DOCTOR").block()),
                "Robert", "Strzykawski", "d n. md.", "strzykawa@gmail.com" , asList(profession, profession3), null, asList(medicalService, medicalService1));
        Doctor doctor3 = new Doctor(new ObjectId(), "panWyleczToSam", "kotMaAle2", singletonList(roleRepository.findByRole("ROLE_DOCTOR").block()),
                "Małgorzata", "Ampułka", "lek.", "ampula54@gmail.com" , asList(profession, profession3), null, asList(medicalService, medicalService1));
        Doctor doctor4 = new Doctor(new ObjectId(), "lekareczka69", "kotMaAle3", singletonList(roleRepository.findByRole("ROLE_DOCTOR").block()),
                "Maria", "Kurczynos", "lek.", "maryska_kurczynos@gmail.com" , asList(profession, profession3), null, asList(medicalService, medicalService1));

        List<Doctor> doctors = asList(doctor, doctor1, doctor2, doctor3, doctor4);

        doctors.forEach(d ->{
            LocalDateTime start = LocalDateTime.of(2019, 5, 10, 8, 0);
            for(int j=0; j<50; j++) {
                for (int i = 0; i < 16; i++) {
                    d.addVisit(new Visit(new ObjectId(), start.plusMinutes(0), start.plusMinutes(30), VisitStatus.AVAILABLE, d, null, null, null, null, null, null, null, null ));
                    start = start.plusMinutes(30);
                }
                start = start.plusHours(16);
            }
        });

        LocalDateTime start = LocalDateTime.of(2019, 4, 10, 8, 0);
        Visit visit = new Visit(new ObjectId(), start.plusMinutes(0), start.plusMinutes(30), VisitStatus.PAID, doctor, patient1, singletonList(disease1), asList(medicalService2, medicalService3), "ból głowy, problemy z oddychaniem", "nawadnianie, odpoczynek", "brak", "brak", "dużo odpoczywać");
        Visit visit1 = new Visit(new ObjectId(), start.plusMinutes(60), start.plusMinutes(90), VisitStatus.PAID, doctor2, patient3, asList(disease4, disease1), singletonList(medicalService), "wysypka", "terapia lekami antyhistaminowymi", "pyłki brzozy, roztocza", "brak", "2x dziennie loratadyna");
        Visit visit2 = new Visit(new ObjectId(), start.plusMinutes(180), start.plusMinutes(210), VisitStatus.PAID, doctor4, patient2, singletonList(disease3), singletonList(medicalService4), "wymioty, otępienie", "nawadnianie", "brak", "alkohol", "na kaca najlepsza jest praca");

        patient1.addVisit(visit);  doctor.addVisit(visit);
        patient3.addVisit(visit1); doctor2.addVisit(visit1);
        patient2.addVisit(visit2); doctor4.addVisit(visit2);

        patientRepository.saveAll(asList(patient, patient1, patient2, patient3, patient4));
        doctorRepository.saveAll(doctors);
    }
}
