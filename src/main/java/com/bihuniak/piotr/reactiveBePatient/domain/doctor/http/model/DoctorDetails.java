package com.bihuniak.piotr.reactiveBePatient.domain.doctor.http.model;

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
public class DoctorDetails {
    String firstName;
    String lastName;
    String title;
    String email;
    List<Long> professions;
    List<Long> services;
}
