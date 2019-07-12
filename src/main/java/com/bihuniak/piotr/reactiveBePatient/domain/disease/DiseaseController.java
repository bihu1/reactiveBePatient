package com.bihuniak.piotr.reactiveBePatient.domain.disease;

import com.bihuniak.piotr.reactiveBePatient.ObjectIdValid;
import com.bihuniak.piotr.reactiveBePatient.domain.disease.http.model.DiseaseDetails;
import com.bihuniak.piotr.reactiveBePatient.domain.disease.http.model.DiseaseUpdate;
import com.bihuniak.piotr.reactiveBePatient.domain.disease.http.model.DiseaseView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/api")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DiseaseController {

    private final DiseaseService diseaseService;

    @GetMapping("/diseases/{diseaseId}")
    @ApiOperation(value="Get disease", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Disease not found")
    })
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<DiseaseView> getDiseases(
            @PathVariable @ObjectIdValid String diseaseId
    ){
       return diseaseService.getDisease(diseaseId);
    }

    @GetMapping("/diseases")
    @ApiOperation(value="Get all diseases", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
    })
    @ResponseStatus(code = HttpStatus.OK)
    public Flux<DiseaseView> getDiseases(){
        return diseaseService.getAllDiseases();
    }

    @PostMapping("/diseases")
    @ApiOperation(value="Add new disease", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created disease"),
            @ApiResponse(code = 400, message = "Request body is not correct")
    })
    @ResponseStatus(code = HttpStatus.CREATED)
    //@Secured({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public Mono<Void> addDisease(@RequestBody @Valid DiseaseDetails diseaseDetails){
        return diseaseService.addDisease(diseaseDetails);
    }

    @PutMapping("/diseases/{diseaseId}")
    @ApiOperation(value="Update disease by Id", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 204, message = "Updated disease"),
            @ApiResponse(code = 400, message = "Request body is not correct"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    //@Secured({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public Mono<ResponseEntity<Void>> updateDisease(
            @PathVariable @ObjectIdValid String diseaseId,
            @RequestBody @Valid DiseaseUpdate diseaseUpdate
    ){
        return diseaseService.updateDisease(diseaseId, diseaseUpdate)
                .map(d -> new ResponseEntity<Void>(HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/disease/{diseaseId}")
    @ApiOperation(value="Delete disease", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 204, message = "Delete disease"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    //@Secured({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public void deleteDisease(
            @PathVariable @ObjectIdValid String diseaseId
    ){
        diseaseService.deleteDisease(diseaseId)
                .map(d -> new ResponseEntity<Void>(HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
