package com.bihuniak.piotr.reactiveBePatient.domain.patient;

import com.dryPepperoniStickTeam.bePatient.common.mail.MailService;
import com.dryPepperoniStickTeam.bePatient.domain.patient.http.model.PatientDetails;
import com.dryPepperoniStickTeam.bePatient.domain.patient.http.model.PatientView;
import com.dryPepperoniStickTeam.bePatient.domain.patient.model.Patient;
import com.dryPepperoniStickTeam.bePatient.domain.user.RoleRepository;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PatientService {

    private final PatientRepository patientRepository;
    private final RoleRepository roleRepository;
    private final MailService mailService;
    private final MapperFacade mapper;

    public List<PatientView> getAllPatients(){
        List<Patient> patients = patientRepository.findAll();
        return mapper.mapAsList(patients, PatientView.class);
    }

    public PatientView getPatient(long patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow(RuntimeException::new);
        return mapper.map(patient, PatientView.class);
    }

    public void register(PatientDetails patientDetails){
        Patient patient = mapper.map(patientDetails, Patient.class);
        patient.setRoles(singletonList(roleRepository.findByRole("ROLE_PATIENT")));
        if(patientRepository.existsByUsername(patient.getUsername())){
            throw new RuntimeException();
        }
        patientRepository.save(patient);
        if(patient.getEmail() != null){
            mailService.sendSimpleMessage(patient.getEmail(),"Rejestracja w bePatient",
                    "Gratulujemy udało Ci się pomyślnie zarejestrować, teraz możesz w pełni korzystać z naszego systemu \n bePatient Admin");
        }
    }

    public void sendMailToReception(String patientId, String message){
        mailService.sendSimpleMessage("bepatientclinic@gmail.com","Wiadomość od użytkownika","Uzytkownik"+patientId+" przesyla wiadomosc"+message);
    }
}
