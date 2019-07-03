package com.bihuniak.piotr.reactiveBePatient.domain.receptionist;

import com.dryPepperoniStickTeam.bePatient.domain.disease.Disease;
import com.dryPepperoniStickTeam.bePatient.domain.disease.DiseaseRepository;
import com.dryPepperoniStickTeam.bePatient.domain.doctor.Doctor;
import com.dryPepperoniStickTeam.bePatient.domain.doctor.DoctorRepository;
import com.dryPepperoniStickTeam.bePatient.domain.patient.PatientRepository;
import com.dryPepperoniStickTeam.bePatient.domain.patient.model.Patient;
import com.dryPepperoniStickTeam.bePatient.domain.profession.Profession;
import com.dryPepperoniStickTeam.bePatient.domain.profession.ProfessionRepository;
import com.dryPepperoniStickTeam.bePatient.domain.service.MedicalService;
import com.dryPepperoniStickTeam.bePatient.domain.service.MedicalServiceRepository;
import com.dryPepperoniStickTeam.bePatient.domain.user.RoleRepository;
import com.dryPepperoniStickTeam.bePatient.domain.visit.Visit;
import com.dryPepperoniStickTeam.bePatient.domain.visit.VisitRepository;
import com.dryPepperoniStickTeam.bePatient.domain.visit.http.model.VisitStatus;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
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
        Profession profession = new Profession(0, "Kardiolog");
        Profession profession1 = new Profession(0, "Nefrolog");
        Profession profession2 = new Profession(0, "Urolog");
        Profession profession3 = new Profession(0, "Dermatolog");
        Profession profession4 = new Profession(0, "Psychiatra");

        professionRepository.saveAll(asList(profession, profession1, profession2, profession3, profession4));

        MedicalService medicalService = new MedicalService(0, "Tomografia", 500);
        MedicalService medicalService1 = new MedicalService(0, "Rezonans Magnetyczny", 1000);
        MedicalService medicalService2 = new MedicalService(0, "Konsultacja dermatologiczna", 250);
        MedicalService medicalService3 = new MedicalService(0, "USG 4D", 400);
        MedicalService medicalService4 = new MedicalService(0, "Laseroterapia", 300);

        medicalServiceRepository.saveAll(asList(medicalService, medicalService1, medicalService2, medicalService3, medicalService4));

        Disease disease = new Disease(0, "Biegunka");
        Disease disease1 = new Disease(0, "Ostoporoza");
        Disease disease2 = new Disease(0, "Jaskra");
        Disease disease3 = new Disease(0, "Częstomocz");
        Disease disease4 = new Disease(0, "Stwardnienie rozsiane");

        diseaseRepository.saveAll(asList(disease, disease1, disease2, disease3, disease4));

        Patient patient = new Patient(0, "90011241122", "alaMakota1", singletonList(roleRepository.findByRole("ROLE_PATIENT")), "andrzej@gmail.com", null, "Jan", "Kozak", "123456789");
        Patient patient1 = new Patient(0, "91011241122", "alaMakota2", singletonList(roleRepository.findByRole("ROLE_PATIENT")), "andrzej@gmail.com", null, "Andrzej", "Lumpacz", "123456789");
        Patient patient2 = new Patient(0, "92011241122", "alaMakota3", singletonList(roleRepository.findByRole("ROLE_PATIENT")), "andrzej@gmail.com", null, "Beata", "Rozstowska", "123456789");
        Patient patient3 = new Patient(0, "93011241122", "alaMakota4", singletonList(roleRepository.findByRole("ROLE_PATIENT")), "andrzej@gmail.com", null, "Kamil", "Kominiok", "123456789");
        Patient patient4 = new Patient(0, "94011241122", "alaMakota5", singletonList(roleRepository.findByRole("ROLE_PATIENT")), "andrzej@gmail.com", null, "Sebastian", "Korzenny", "123456789");

        Doctor doctor = new Doctor(0, "doctorHouse", "kotMaAle", singletonList(roleRepository.findByRole("ROLE_DOCTOR")),
                "Aleksander", "Ziółko", "dr n. md.", "ziolko@gmail.com" , asList(profession, profession3), null, asList(medicalService, medicalService1));
        Doctor doctor1 = new Doctor(0, "missMedico", "kotMaAle1", singletonList(roleRepository.findByRole("ROLE_DOCTOR")),
                "Grzegorz", "Lecznicki", "dr n. md.", "grzegorz_lecznicki@gmail.com" , asList(profession, profession3), null, asList(medicalService, medicalService1));
        Doctor doctor2 = new Doctor(0, "drChoroba52", "kotMaAle", singletonList(roleRepository.findByRole("ROLE_DOCTOR")),
                "Robert", "Strzykawski", "d n. md.", "strzykawa@gmail.com" , asList(profession, profession3), null, asList(medicalService, medicalService1));
        Doctor doctor3 = new Doctor(0, "panWyleczToSam", "kotMaAle2", singletonList(roleRepository.findByRole("ROLE_DOCTOR")),
                "Małgorzata", "Ampułka", "lek.", "ampula54@gmail.com" , asList(profession, profession3), null, asList(medicalService, medicalService1));
        Doctor doctor4 = new Doctor(0, "lekareczka69", "kotMaAle3", singletonList(roleRepository.findByRole("ROLE_DOCTOR")),
                "Maria", "Kurczynos", "lek.", "maryska_kurczynos@gmail.com" , asList(profession, profession3), null, asList(medicalService, medicalService1));

        List<Doctor> doctors = asList(doctor, doctor1, doctor2, doctor3, doctor4);

        doctors.forEach(d ->{
            LocalDateTime start = LocalDateTime.of(2019, 5, 10, 8, 0);
            for(int j=0; j<50; j++) {
                for (int i = 0; i < 16; i++) {
                    d.addVisit(new Visit(0, start.plusMinutes(0), start.plusMinutes(30), VisitStatus.AVAILABLE, d, null, null, null, null, null, null, null, null ));
                    start = start.plusMinutes(30);
                }
                start = start.plusHours(16);
            }
        });

        LocalDateTime start = LocalDateTime.of(2019, 4, 10, 8, 0);
        Visit visit = new Visit(0, start.plusMinutes(0), start.plusMinutes(30), VisitStatus.PAID, doctor, patient1, singletonList(disease1), asList(medicalService2, medicalService3), "ból głowy, problemy z oddychaniem", "nawadnianie, odpoczynek", "brak", "brak", "dużo odpoczywać");
        Visit visit1 = new Visit(0, start.plusMinutes(60), start.plusMinutes(90), VisitStatus.PAID, doctor2, patient3, asList(disease4, disease1), singletonList(medicalService), "wysypka", "terapia lekami antyhistaminowymi", "pyłki brzozy, roztocza", "brak", "2x dziennie loratadyna");
        Visit visit2 = new Visit(0, start.plusMinutes(180), start.plusMinutes(210), VisitStatus.PAID, doctor4, patient2, singletonList(disease3), singletonList(medicalService4), "wymioty, otępienie", "nawadnianie", "brak", "alkohol", "na kaca najlepsza jest praca");

        patient1.addVisit(visit);  doctor.addVisit(visit);
        patient3.addVisit(visit1); doctor2.addVisit(visit1);
        patient2.addVisit(visit2); doctor4.addVisit(visit2);

        patientRepository.saveAll(asList(patient, patient1, patient2, patient3, patient4));
        doctorRepository.saveAll(doctors);
    }
}
