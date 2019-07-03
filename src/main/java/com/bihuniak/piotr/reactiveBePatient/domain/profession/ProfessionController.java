package com.bihuniak.piotr.reactiveBePatient.domain.profession;

import com.dryPepperoniStickTeam.bePatient.domain.profession.http.model.ProfessionDetails;
import com.dryPepperoniStickTeam.bePatient.domain.profession.http.model.ProfessionUpdate;
import com.dryPepperoniStickTeam.bePatient.domain.profession.http.model.ProfessionView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProfessionController {

    private final ProfessionService professionService;

    @GetMapping("/professions/{professionId}")
    @ApiOperation(value="Get profession", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Profession not found")
    })
    @ResponseStatus(code = HttpStatus.OK)
    public ProfessionView getProfession(
            @PathVariable long professionId
    ){
       return professionService.getProfession(professionId);
    }

    @GetMapping("/professions")
    @ApiOperation(value="Get all professions", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
    })
    @ResponseStatus(code = HttpStatus.OK)
    public List<ProfessionView> getProfession(){
        return professionService.getAllProfessions();
    }

    @PostMapping("/professions")
    @ApiOperation(value="Add new profession", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created profession"),
            @ApiResponse(code = 400, message = "Request body is not correct")
    })
    @ResponseStatus(code = HttpStatus.CREATED)
    @Secured("ROLE_ADMIN")
    public void addProfession(@RequestBody @Valid ProfessionDetails professionDetails){
        professionService.addProfession(professionDetails);
    }

    @PutMapping("/professions/{professionId}")
    @ApiOperation(value="Update profession with given id", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 204, message = "Updated profession"),
            @ApiResponse(code = 400, message = "Request body is not correct"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Secured("ROLE_ADMIN")
    public void updateProfession(
            @PathVariable long professionId,
            @RequestBody @Valid ProfessionUpdate professionUpdate
    ){
        professionService.updateProfession(professionId, professionUpdate);
    }

    @DeleteMapping("/professions/{professionId}")
    @ApiOperation(value="Delete profession", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 204, message = "Profession deleted"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Secured("ROLE_ADMIN")
    public void deleteProfession(
            @PathVariable long professionId
    ){
        professionService.deleteProfession(professionId);
    }
}
