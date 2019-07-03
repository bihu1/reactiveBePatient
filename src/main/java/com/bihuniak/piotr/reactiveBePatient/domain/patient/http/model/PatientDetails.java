package com.bihuniak.piotr.reactiveBePatient.domain.patient.http.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class PatientDetails {

    @NotEmpty
    String firstName;

    @NotEmpty
    String lastName;

    @NotEmpty
    @Pattern(regexp = "[0-9]{4}[0-3]{1}[0-9}{1}][0-9]{5}")
    String pesel;

    @Pattern(regexp = "[a-zA-Z0-9_\\.\\+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-\\.]+")
    String email;

    @NotEmpty
    @Pattern(regexp = "\\+?\\(?\\d{2,4}\\)?[\\d\\s-]{3,}")
    String phone;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
    String password;
}
