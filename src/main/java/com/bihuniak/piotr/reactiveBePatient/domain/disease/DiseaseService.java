package com.bihuniak.piotr.reactiveBePatient.domain.disease;

import com.dryPepperoniStickTeam.bePatient.domain.disease.http.model.DiseaseDetails;
import com.dryPepperoniStickTeam.bePatient.domain.disease.http.model.DiseaseUpdate;
import com.dryPepperoniStickTeam.bePatient.domain.disease.http.model.DiseaseView;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;
    private final MapperFacade mapper;

    public DiseaseView getDisease(long id){
        Disease disease = diseaseRepository.findById(id).orElseThrow(RuntimeException::new);
        return mapper.map(disease, DiseaseView.class);
    }

    public List<DiseaseView> getAllDiseases(){
        List<Disease> diseases = diseaseRepository.findAll();
        return mapper.mapAsList(diseases, DiseaseView.class);
    }

    public void addDisease(DiseaseDetails diseaseDetails){
        Disease disease = mapper.map(diseaseDetails, Disease.class);
        diseaseRepository.save(disease);
    }

    public void updateDisease(long diseaseId, DiseaseUpdate diseaseUpdate){
        if(!diseaseRepository.existsById(diseaseId)){
            throw new RuntimeException();
        }
        Disease disease = mapper.map(diseaseUpdate, Disease.class);
        disease.setId(diseaseId);
        diseaseRepository.save(disease);
    }

    public void deleteDisease(long diseaseId){
        if(!diseaseRepository.existsById(diseaseId)){
            throw new RuntimeException();
        }
        diseaseRepository.deleteById(diseaseId);
    }
}
