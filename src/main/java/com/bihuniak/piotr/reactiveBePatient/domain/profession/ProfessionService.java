package com.bihuniak.piotr.reactiveBePatient.domain.profession;

import com.dryPepperoniStickTeam.bePatient.domain.profession.http.model.ProfessionDetails;
import com.dryPepperoniStickTeam.bePatient.domain.profession.http.model.ProfessionUpdate;
import com.dryPepperoniStickTeam.bePatient.domain.profession.http.model.ProfessionView;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProfessionService {

    private final ProfessionRepository professionRepository;
    private final MapperFacade mapper;

    public ProfessionView getProfession(long id){
        Profession profession = professionRepository.findById(id).orElseThrow(RuntimeException::new);
        return mapper.map(profession, ProfessionView.class);
    }

    public List<ProfessionView> getAllProfessions(){
        List<Profession> professions = professionRepository.findAll();
        return mapper.mapAsList(professions, ProfessionView.class);
    }

    public void addProfession(ProfessionDetails professionDetails){
        Profession profession = mapper.map(professionDetails, Profession.class);
        professionRepository.save(profession);
    }

    public void updateProfession(long professionId, ProfessionUpdate professionUpdate){
        if(!professionRepository.existsById(professionId)){
            throw new RuntimeException();
        }
        Profession profession = mapper.map(professionUpdate, Profession.class);
        profession.setId(professionId);
        professionRepository.save(profession);
    }

    public void deleteProfession(long professionId){
        if(!professionRepository.existsById(professionId)){
            throw new RuntimeException();
        }
        professionRepository.deleteById(professionId);
    }
}
