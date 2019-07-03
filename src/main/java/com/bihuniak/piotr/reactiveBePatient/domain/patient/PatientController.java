package com.bihuniak.piotr.reactiveBePatient.domain.patient;

import com.dryPepperoniStickTeam.bePatient.config.security.SecurityUserDetails;
import com.dryPepperoniStickTeam.bePatient.domain.patient.http.model.PatientDetails;
import com.dryPepperoniStickTeam.bePatient.domain.patient.http.model.PatientView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PatientController {

    private final PatientService patientService;

    @GetMapping("api/patients")
    @ApiOperation(value="Get all patients", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
    })
    @ResponseStatus(code = HttpStatus.OK)
    @Secured("ROLE_ADMIN")
    public List<PatientView> getAllPatients(){
        return patientService.getAllPatients();
    }

    @GetMapping("api/patients/{patientId}")
    @ApiOperation(value="Get all patients", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
    })
    @ResponseStatus(code = HttpStatus.OK)
    public PatientView getPatient(@PathVariable long patientId){
        return patientService.getPatient(patientId);
    }

    @PostMapping("patients/registration")
    @ApiOperation(value="Register new patient")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created patient"),
            @ApiResponse(code = 400, message = "Request body is not correct")
    })
    @ResponseStatus(code = HttpStatus.CREATED)
    public void register(@RequestBody @Valid PatientDetails patientDetails){
        patientService.register(patientDetails);
    }

    @PostMapping("api/patient/message")
    @ApiOperation(value="Send message to Reception, it figure out current logged user id", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Send message"),
            @ApiResponse(code = 400, message = "Request body is not correct")
    })
    @ResponseStatus(code = HttpStatus.CREATED)
    @Secured("ROLE_PATIENT")
    public void sendMailToReception(@ApiIgnore @AuthenticationPrincipal SecurityUserDetails userDetails, @RequestBody String message){
        patientService.sendMailToReception(String.valueOf(userDetails.getId()), message);
    }
}
