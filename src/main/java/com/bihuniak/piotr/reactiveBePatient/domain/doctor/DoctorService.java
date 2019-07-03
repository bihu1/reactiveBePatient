package com.bihuniak.piotr.reactiveBePatient.domain.doctor;

import com.dryPepperoniStickTeam.bePatient.common.mail.MailService;
import com.dryPepperoniStickTeam.bePatient.domain.doctor.http.model.DoctorDetails;
import com.dryPepperoniStickTeam.bePatient.domain.doctor.http.model.DoctorUpdate;
import com.dryPepperoniStickTeam.bePatient.domain.doctor.http.model.DoctorView;
import com.dryPepperoniStickTeam.bePatient.domain.profession.Profession;
import com.dryPepperoniStickTeam.bePatient.domain.profession.ProfessionRepository;
import com.dryPepperoniStickTeam.bePatient.domain.service.MedicalService;
import com.dryPepperoniStickTeam.bePatient.domain.service.MedicalServiceRepository;
import com.dryPepperoniStickTeam.bePatient.domain.user.RoleRepository;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final RoleRepository roleRepository;
    private final MedicalServiceRepository medicalServiceRepository;
    private final ProfessionRepository professionRepository;
    private final MailService mailService;
    private final MapperFacade mapper;

    public List<DoctorView> getAllDoctors(){
        List<Doctor> doctors = doctorRepository.findAll();
        return mapper.mapAsList(doctors, DoctorView.class);
    }

    public DoctorView getDoctor(long id){
        Doctor doctor = doctorRepository.findById(id).orElseThrow(RuntimeException::new);
        return mapper.map(doctor, DoctorView.class);
    }

    public void addDoctor(DoctorDetails doctorDetails){
        Doctor doctor = mapper.map(doctorDetails, Doctor.class);

        List<Profession> professions = professionRepository.findByIdIn(doctorDetails.getProfessions());
        checkIfIndexesWerePulled(professions, doctorDetails.getProfessions())
                .ifPresent(x -> x.accept("One of profession index is incorrect"));

        List<MedicalService> medicalServices = medicalServiceRepository.findByIdIn(doctorDetails.getServices());
        checkIfIndexesWerePulled(medicalServices, doctorDetails.getServices())
                .ifPresent(x -> x.accept("One of medical service index is incorrect"));

        doctor.setMedicalServices(medicalServices);
        doctor.setProfessions(professions);
        doctor.setUsername(UUID.randomUUID().toString());
        String doctorPassword = UUID.randomUUID().toString();
        doctor.setPassword(doctorPassword);
        doctor.setRoles(singletonList(roleRepository.findByRole("ROLE_DOCTOR")));
        doctorRepository.save(doctor);
        mailService.sendSimpleMessage(doctor.getEmail(),"Rejestracja w bePatient",
                "Gratulujemy utworzono Ci konto w aplikacji bePatient Twój: \n login: "+doctor.getUsername()+" \n hasło:"+doctorPassword+"\nLogin i hasło należy zmienić po pierwszym logowaniu. \n bePatient Admin");
    }

    public void updateDoctor(long doctorId, DoctorUpdate doctorUpdate) {
        if(!doctorRepository.existsById(doctorId)){
            throw new RuntimeException();
        }
        Doctor doctor = mapper.map(doctorUpdate, Doctor.class);

        List<MedicalService> medicalServices = medicalServiceRepository.findByIdIn(doctorUpdate.getServices());
        checkIfIndexesWerePulled(medicalServices, doctorUpdate.getServices())
                .ifPresent(x -> x.accept("One of medical service index is incorrect"));

        List<Profession> professions = professionRepository.findByIdIn(doctorUpdate.getProfessions());
        checkIfIndexesWerePulled(professions, doctorUpdate.getServices())
                .ifPresent(x -> x.accept("One of profession index is incorrect"));

        doctor.setMedicalServices(medicalServices);
        doctor.setProfessions(professions);
        doctorRepository.save(doctor);
    }

    public void deleteDoctor(long doctorId){
        if(!doctorRepository.existsById(doctorId)){
            throw new RuntimeException();
        }
        doctorRepository.deleteById(doctorId);
    }

    private static<T,V> Optional<Consumer<String>> checkIfIndexesWerePulled(List<T> list1, List<V> list2){
        if(list1 == null || list2 == null){
            return Optional.empty();
        }
        if(list1.size() != list2.size()){
           return Optional.of(i -> {throw new RuntimeException(i);});
        }
        return Optional.empty();
    }
}
