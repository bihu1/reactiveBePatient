package com.bihuniak.piotr.reactiveBePatient.domain.doctor;

import com.bihuniak.piotr.reactiveBePatient.domain.profession.Profession;
import com.bihuniak.piotr.reactiveBePatient.domain.service.MedicalService;
import com.bihuniak.piotr.reactiveBePatient.domain.user.model.User;
import com.bihuniak.piotr.reactiveBePatient.domain.user.model.UserRole;
import com.bihuniak.piotr.reactiveBePatient.domain.visit.Visit;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, exclude = "visits")
@EqualsAndHashCode(callSuper = true, exclude = "visits")
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Doctor extends User {

    String firstName;
    String lastName;
    String title;
    String email;

    @DBRef
    List<Profession> professions;
    @DBRef
    List<Visit> visits;
    @DBRef
    List<MedicalService> medicalServices;

    public Doctor(ObjectId id, String username, String password, List<UserRole> roles, String firstName, String lastName, String title, String email, List<Profession> professions, List<Visit> visits, List<MedicalService> medicalServices) {
        super(id, username, password, roles);
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.email = email;
        this.professions = professions;
        this.visits = visits;
        this.medicalServices = medicalServices;
    }

    public void addVisit(Visit visit){
        if(visits == null){
            visits = new ArrayList<>();
        }
        visits.add(visit);
    }
}
