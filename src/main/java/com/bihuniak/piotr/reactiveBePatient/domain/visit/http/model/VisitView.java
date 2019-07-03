package com.bihuniak.piotr.reactiveBePatient.domain.visit.http.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class VisitView {

    long id;
    Long patientId;
    LocalDateTime dateFrom;
    LocalDateTime dateTo;
    VisitStatus status;
}
