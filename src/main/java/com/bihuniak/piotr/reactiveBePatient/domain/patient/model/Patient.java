package com.bihuniak.piotr.reactiveBePatient.domain.patient.model;

import com.bihuniak.piotr.reactiveBePatient.domain.user.model.User;
import com.bihuniak.piotr.reactiveBePatient.domain.user.model.UserRole;
import com.bihuniak.piotr.reactiveBePatient.domain.visit.Visit;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Patient extends User {

    String firstName;
    String lastName;
    String email;
    String phone;
    @DBRef
    List<Visit> visits;

    public Patient(long id, String username, String password, List<UserRole> roles, String email, List<Visit> visits, String firstName, String lastName, String phone) {
        super(id, username, password, roles);
        this.email = email;
        this.visits = visits;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public void addVisit(Visit visit){
        if(visits == null){
            visits = new ArrayList<>();
        }
        visits.add(visit);
    }
}
