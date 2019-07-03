package com.bihuniak.piotr.reactiveBePatient.domain.patient.http.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class PatientView {

    long id;
    String firstName;
    String lastName;
    String pesel;
    String email;
    String phone;
}
