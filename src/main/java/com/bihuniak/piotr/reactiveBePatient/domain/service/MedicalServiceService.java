package com.bihuniak.piotr.reactiveBePatient.domain.service;

import com.dryPepperoniStickTeam.bePatient.domain.service.http.model.MedicalServiceDetails;
import com.dryPepperoniStickTeam.bePatient.domain.service.http.model.MedicalServiceUpdate;
import com.dryPepperoniStickTeam.bePatient.domain.service.http.model.MedicalServiceView;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MedicalServiceService {

    private final MedicalServiceRepository medicalServiceRepository;
    private final MapperFacade mapper;

    public MedicalServiceView getMedicalService(long id){
        MedicalService medicalService = medicalServiceRepository.findById(id).orElseThrow(RuntimeException::new);
        return mapper.map(medicalService, MedicalServiceView.class);
    }

    public List<MedicalServiceView> getAllMedicalServices(){
        List<MedicalService> medicalServices = medicalServiceRepository.findAll();
        return mapper.mapAsList(medicalServices, MedicalServiceView.class);
    }

    public void addMedicalService(MedicalServiceDetails medicalServiceDetails){
        MedicalService medicalService = mapper.map(medicalServiceDetails, MedicalService.class);
        medicalServiceRepository.save(medicalService);
    }

    public void updateMedicalService(long medicalServiceId, MedicalServiceUpdate medicalServiceUpdate){
        if(!medicalServiceRepository.existsById(medicalServiceId)){
            throw new RuntimeException();
        }
        MedicalService medicalService = mapper.map(medicalServiceUpdate, MedicalService.class);
        medicalService.setId(medicalServiceId);
        medicalServiceRepository.save(medicalService);
    }

    public void deleteMedicalService(long medicalServiceId){
        if(!medicalServiceRepository.existsById(medicalServiceId)){
            throw new RuntimeException();
        }
        medicalServiceRepository.deleteById(medicalServiceId);
    }
}
