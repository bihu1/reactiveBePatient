package com.bihuniak.piotr.reactiveBePatient.domain.doctor.http.model;

import com.dryPepperoniStickTeam.bePatient.domain.profession.http.model.ProfessionView;
import com.dryPepperoniStickTeam.bePatient.domain.service.http.model.MedicalServiceView;
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
public class DoctorView {
    long id;
    String firstName;
    String lastName;
    String title;
    List<ProfessionView> professions;
    List<MedicalServiceView> services;
}
