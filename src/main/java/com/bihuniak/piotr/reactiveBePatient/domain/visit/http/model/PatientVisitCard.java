package com.bihuniak.piotr.reactiveBePatient.domain.visit.http.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class PatientVisitCard {

    List<String> services;
    List<String> diseases;
    String mainSymptoms;
    String treatment;
    String allergy;
    String addiction;
    String comment;
}
