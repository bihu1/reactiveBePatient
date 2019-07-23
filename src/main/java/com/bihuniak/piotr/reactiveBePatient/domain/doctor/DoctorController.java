package com.bihuniak.piotr.reactiveBePatient.domain.doctor;

import com.bihuniak.piotr.reactiveBePatient.ObjectIdValid;
import com.bihuniak.piotr.reactiveBePatient.domain.doctor.http.model.DoctorDetails;
import com.bihuniak.piotr.reactiveBePatient.domain.doctor.http.model.DoctorUpdate;
import com.bihuniak.piotr.reactiveBePatient.domain.doctor.http.model.DoctorView;
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

@Validated
@RestController
@RequestMapping("/api/doctors")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    @ApiOperation(value = "Get all doctors", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
    })
    @ResponseStatus(HttpStatus.OK)
    public Flux<DoctorView> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{doctorId}")
    @ApiOperation(value = "Get doctor", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Doctor not found"),
    })
    @ResponseStatus(HttpStatus.OK)
    public Mono<DoctorView> getAllDoctors(@PathVariable @ObjectIdValid String doctorId) {
        return doctorService.getDoctor(doctorId);
    }

    @PostMapping
    @ApiOperation(value = "Add new doctor", authorizations = {@Authorization("Bearer <oAuth2>")} )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
    })
    @ResponseStatus(HttpStatus.OK)
    //@Secured("ROLE_ADMIN")
    public Mono<Void> addDoctor(@RequestBody DoctorDetails doctorDetails) {
        return doctorService.addDoctor(doctorDetails);
    }

    @PutMapping("/{doctorId}")
    @ApiOperation(value = "Update doctor by Id", authorizations = {@Authorization("Bearer <oAuth2>")} )
    @ApiResponses({
            @ApiResponse(code = 204, message = "Doctor updated"),
            @ApiResponse(code = 404, message = "Doctor not found"),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@Secured("ROLE_ADMIN")
    public Mono<Void> updateDoctor(@PathVariable @ObjectIdValid String doctorId, @RequestBody DoctorUpdate doctorUpdate) {
        return doctorService.updateDoctor(doctorId, doctorUpdate);
    }

    @DeleteMapping("/{doctorId}")
    @ApiOperation(value = "Delete doctor by Id", authorizations = {@Authorization("Bearer <oAuth2>")} )
    @ApiResponses({
            @ApiResponse(code = 204, message = "Doctor deleted"),
            @ApiResponse(code = 404, message = "Doctor not found"),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@Secured("ROLE_ADMIN")
    public Mono<Void> deleteDoctor(@PathVariable @ObjectIdValid String doctorId) {
        return doctorService.deleteDoctor(doctorId);
    }
}
