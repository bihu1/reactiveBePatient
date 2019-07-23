package com.bihuniak.piotr.reactiveBePatient.domain.user.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public abstract class User {

    @Id
    ObjectId id;
    String username;
    String password;
    @DBRef
    List<UserRole> roles;

    public User(ObjectId id, String username, String password, List<UserRole> roles) {
        this.id = id;
        this.username = username;
        this.password = password;//= new BCryptPasswordEncoder().encode(password);
        this.roles = roles;
    }

//    public void setPassword(String password){
//       this.password = new BCryptPasswordEncoder().encode(password);
//    }
}
