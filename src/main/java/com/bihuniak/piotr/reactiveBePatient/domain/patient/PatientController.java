package com.bihuniak.piotr.reactiveBePatient.domain.patient;

import com.bihuniak.piotr.reactiveBePatient.ObjectIdValid;
import com.bihuniak.piotr.reactiveBePatient.domain.patient.http.model.PatientDetails;
import com.bihuniak.piotr.reactiveBePatient.domain.patient.http.model.PatientView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Validated
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
    //@Secured("ROLE_ADMIN")
    public Flux<PatientView> getAllPatients(){
        return patientService.getAllPatients();
    }

    @GetMapping("api/patients/{patientId}")
    @ApiOperation(value="Get all patients", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
    })
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<PatientView> getPatient(@PathVariable @ObjectIdValid String patientId){
        return patientService.getPatient(patientId);
    }

    @PostMapping("patients/registration")
    @ApiOperation(value="Register new patient")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created patient"),
            @ApiResponse(code = 400, message = "Request body is not correct")
    })
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<Void> register(@RequestBody @Valid PatientDetails patientDetails){
        return patientService.register(patientDetails);
    }

    @PostMapping("api/patient/message")
    @ApiOperation(value="Send message to Reception, it figure out current logged user id", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Send message"),
            @ApiResponse(code = 400, message = "Request body is not correct")
    })
    @ResponseStatus(code = HttpStatus.CREATED)
    //@Secured("ROLE_PATIENT")
    public Mono<Void> sendMailToReception(/*@ApiIgnore @AuthenticationPrincipal SecurityUserDetails userDetails, */@RequestBody String message){
        //patientService.sendMailToReception(String.valueOf(userDetails.getId()), message);
        return Mono.empty();
    }
}
