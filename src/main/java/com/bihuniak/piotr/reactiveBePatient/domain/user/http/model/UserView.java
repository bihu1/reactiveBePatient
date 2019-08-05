package com.bihuniak.piotr.reactiveBePatient.domain.user.http.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserView {
    String id;
    List<String> roles;
}
