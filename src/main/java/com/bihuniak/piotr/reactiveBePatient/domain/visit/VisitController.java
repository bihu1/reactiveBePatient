package com.bihuniak.piotr.reactiveBePatient.domain.visit;

import com.dryPepperoniStickTeam.bePatient.common.IdHolder;
import com.dryPepperoniStickTeam.bePatient.domain.visit.http.model.*;
import com.itextpdf.text.pdf.BaseFont;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public List<VisitView> getAllDoctorsVisitsByStatus(
            @PathVariable long doctorId,
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
    public List<VisitView> getAllVisits(){
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
    @Secured("ROLE_ADMIN")
    public void addDoctorAvailableVisit(@PathVariable long doctorId, @RequestBody VisitDetails visitDetails){
        visitService.addDoctorAvailableVisit(doctorId, visitDetails);
    }

    @PostMapping("/doctors/{doctorId}/visits/{visitId}")
    @ApiOperation(value = "Reserve visit for patient", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
    })
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_PATIENT")
    public void reserveVisitByPanelist(
            @PathVariable long doctorId,
            @PathVariable long visitId,
            @RequestBody IdHolder patientIdHolder
    ){
        visitService.reserveVisitByPatient(doctorId, visitId, patientIdHolder.getId());
    }

    @GetMapping("/patients/{patientId}/visits")
    @ApiOperation(value = "Get all patient's visits", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
    })
    @ResponseStatus(HttpStatus.OK)
    public List<ReservedVisitView> getPatientsVisits(@PathVariable long patientId){
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
    @Secured({ "ROLE_DOCTOR", "ROLE_ADMIN" })
    public void assignDiseaseAndServicesToVisit(@PathVariable long visitId, @RequestBody PatientVisitCard patientVisitCard){
         visitService.assignDiseaseAndMedicalServices(visitId, patientVisitCard);
    }

    @PutMapping("/visits/{visitId}")
    @ApiOperation(value = "Change visit status", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
    })
    @ResponseStatus(HttpStatus.OK)
    @Secured({ "ROLE_DOCTOR", "ROLE_ADMIN" })
    public void changeVisitStatus(@PathVariable long visitId, @RequestParam VisitStatus status){
        visitService.changeVisitStatus(visitId, status);
    }

    @GetMapping("/visits/{visitId}/pdf/generation")
    @ApiOperation(value="Generate pdf file", authorizations = {@Authorization("Bearer <oAuth2>")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
    })
    @ResponseStatus(code = HttpStatus.CREATED)
    @SneakyThrows
    @Secured({ "ROLE_DOCTOR", "ROLE_ADMIN" })
    public ResponseEntity generatePdf(@PathVariable long visitId){
        String html = visitService.generateHTMLView(visitId);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver().addFont("fonts/ARIALUNI.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=dane_osobowe.pdf")
                .contentType(new MediaType("application", "pdf", Charset.forName("utf-8")))
                .body(new InputStreamResource(inputStream));
    }
}
