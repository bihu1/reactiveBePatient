package com.bihuniak.piotr.reactiveBePatient.domain.service;

import com.bihuniak.piotr.reactiveBePatient.ObjectIdValid;
import com.bihuniak.piotr.reactiveBePatient.domain.service.http.model.MedicalServiceDetails;
import com.bihuniak.piotr.reactiveBePatient.domain.service.http.model.MedicalServiceUpdate;
import com.bihuniak.piotr.reactiveBePatient.domain.service.http.model.MedicalServiceView;
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

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/api")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MedicalServiceController {

    private final MedicalServiceService medicalServiceService;

    @GetMapping("/services/{serviceId}")
    @ApiOperation(value="Get medical service", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<MedicalServiceView> getMedicalService(
            @PathVariable @ObjectIdValid String serviceId
    ){
       return medicalServiceService.getMedicalService(serviceId);
    }

    @GetMapping("/services")
    @ApiOperation(value="Get all medical services", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
    })
    @ResponseStatus(code = HttpStatus.OK)
    public Flux<MedicalServiceView> getMedicalService(){
        return medicalServiceService.getAllMedicalServices();
    }

    @PostMapping("/services")
    @ApiOperation(value="Add new medical service", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created medical service"),
            @ApiResponse(code = 400, message = "Request body is not correct")
    })
    @ResponseStatus(code = HttpStatus.CREATED)
    //@Secured("ROLE_ADMIN")
    public Mono<Void> addMedicalService(@RequestBody @Valid MedicalServiceDetails medicalServiceDetails){
       return medicalServiceService.addMedicalService(medicalServiceDetails);
    }

    @PutMapping("/services/{serviceId}")
    @ApiOperation(value="Update medical service", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 204, message = "Updated medical service"),
            @ApiResponse(code = 400, message = "Request body is not correct"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    //@Secured("ROLE_ADMIN")
    public Mono<Void> updateMedicalService(
            @PathVariable @ObjectIdValid String serviceId,
            @RequestBody @Valid MedicalServiceUpdate medicalServiceUpdate
    ){
       return medicalServiceService.updateMedicalService(serviceId, medicalServiceUpdate);
    }

    @DeleteMapping("/services/{serviceId}")
    @ApiOperation(value="Delete medical service", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 204, message = "Delete medical service"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    //@Secured("ROLE_ADMIN")
    public Mono<Void> deleteMedicalService(
            @PathVariable @ObjectIdValid String serviceId
    ){
       return medicalServiceService.deleteMedicalService(serviceId);
    }
}
