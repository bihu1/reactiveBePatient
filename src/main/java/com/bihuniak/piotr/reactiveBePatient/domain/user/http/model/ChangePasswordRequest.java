package com.bihuniak.piotr.reactiveBePatient.domain.user.http.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class ChangePasswordRequest {

    String oldPassword;
    String newPassword;
}
