package com.bihuniak.piotr.reactiveBePatient.domain.visit;

import com.bihuniak.piotr.reactiveBePatient.ObjectIdValid;
import com.bihuniak.piotr.reactiveBePatient.common.IdHolder;
import com.bihuniak.piotr.reactiveBePatient.domain.visit.http.model.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/api")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VisitController {

    private final VisitService visitService;

    @GetMapping("/doctors/{doctorId}/visits")
    @ApiOperation(value = "Get all available doctor's visits", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
    })
    @ResponseStatus(HttpStatus.OK)
    public Flux<VisitView> getAllDoctorsVisitsByStatus(
            @PathVariable @ObjectIdValid String doctorId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> date,
            @RequestParam FilterVisitStatus patternVisitStatus
    ){
        return visitService.getAllDoctorsVisitsByStatus(doctorId, date, patternVisitStatus);
    }

    @GetMapping("/visits")
    @ApiOperation(value = "Get all visits", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
    })
    @ResponseStatus(HttpStatus.OK)
    public Flux<VisitView> getAllVisits(){
        return visitService.getAllVisits();
    }

    @PostMapping("/doctors/{doctorId}/visits")
    @ApiOperation(value = "Add available visit to given doctor", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 200, message = "Bad request"),
            @ApiResponse(code = 404, message = "Doctor not found"),
    })
    @ResponseStatus(HttpStatus.OK)
    //@Secured("ROLE_ADMIN")
    public Mono<Void> addDoctorAvailableVisit(@PathVariable @ObjectIdValid String doctorId, @RequestBody VisitDetails visitDetails){
        return visitService.addDoctorAvailableVisit(doctorId, visitDetails);
    }

    @PostMapping("/doctors/{doctorId}/visits/{visitId}")
    @ApiOperation(value = "Reserve visit for patient", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
    })
    @ResponseStatus(HttpStatus.OK)
    //@Secured("ROLE_PATIENT")
    public Mono<Void> reserveVisitByPanelist(
            @PathVariable @ObjectIdValid String doctorId,
            @PathVariable @ObjectIdValid String visitId,
            @RequestBody @Valid IdHolder patientIdHolder
    ){
        return visitService.reserveVisitByPatient(doctorId, visitId, patientIdHolder.getId());
    }

    @GetMapping("/patients/{patientId}/visits")
    @ApiOperation(value = "Get all patient's visits", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
    })
    @ResponseStatus(HttpStatus.OK)
    public Flux<ReservedVisitView> getPatientsVisits(@PathVariable @ObjectIdValid String patientId){
        return visitService.getAllPatientsVisits(patientId);
    }

    @PostMapping("/visits/{visitId}/assign")
    @ApiOperation(value = "Assign diseases and medical services with all doctor comment to visit", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
    })
    @ResponseStatus(HttpStatus.OK)
    //@Secured({ "ROLE_DOCTOR", "ROLE_ADMIN" })
    public Mono<Void> assignDiseaseAndServicesToVisit(@PathVariable @ObjectIdValid String visitId, @RequestBody PatientVisitCard patientVisitCard){
        return visitService.assignDiseaseAndMedicalServices(visitId, patientVisitCard);
    }

    @PutMapping("/visits/{visitId}")
    @ApiOperation(value = "Change visit status", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
    })
    @ResponseStatus(HttpStatus.OK)
    //@Secured({ "ROLE_DOCTOR", "ROLE_ADMIN" })
    public Mono<Void> changeVisitStatus(@PathVariable @ObjectIdValid String visitId, @RequestParam VisitStatus status){
        return visitService.changeVisitStatus(visitId, status);
    }

    @GetMapping("/visits/{visitId}/pdf/generation")
    @ApiOperation(value="Generate pdf file", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
    })
    @ResponseStatus(code = HttpStatus.CREATED)
    @SneakyThrows
    //@Secured({ "ROLE_DOCTOR", "ROLE_ADMIN" })
    public ResponseEntity generatePdf(@PathVariable @ObjectIdValid String visitId){
        String html = visitService.generateHTMLView(visitId);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ITextRenderer renderer = new ITextRenderer();
//        renderer.getFontResolver().addFont("fonts/ARIALUNI.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//        renderer.setDocumentFromString(html);
//        renderer.layout();
//        renderer.createPDF(outputStream);
        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=dane_osobowe.pdf")
                .contentType(new MediaType("application", "pdf", Charset.forName("utf-8")))
                .body(new InputStreamResource(inputStream));
    }
}
