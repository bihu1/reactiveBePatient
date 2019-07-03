package com.bihuniak.piotr.reactiveBePatient.domain.visit;

import com.bihuniak.piotr.reactiveBePatient.domain.disease.Disease;
import com.bihuniak.piotr.reactiveBePatient.domain.doctor.Doctor;
import com.bihuniak.piotr.reactiveBePatient.domain.patient.model.Patient;
import com.bihuniak.piotr.reactiveBePatient.domain.service.MedicalService;
import com.bihuniak.piotr.reactiveBePatient.domain.visit.http.model.VisitStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Visit {

    @Id
    ObjectId id;
    LocalDateTime dateFrom;
    LocalDateTime dateTo;
    VisitStatus status;

    @DBRef
    Doctor doctor;

    @DBRef
    Patient patient;

    @DBRef
    List<Disease> diseases;

    @DBRef
    List<MedicalService> medicalServices;

    String mainSymptoms;
    String treatment;
    String allergy;
    String addiction;
    String comment;
}
