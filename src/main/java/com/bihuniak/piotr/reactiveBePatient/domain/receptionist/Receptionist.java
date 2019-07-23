package com.bihuniak.piotr.reactiveBePatient.domain.receptionist;

import com.bihuniak.piotr.reactiveBePatient.domain.user.model.User;
import com.bihuniak.piotr.reactiveBePatient.domain.user.model.UserRole;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Receptionist extends User {
    public Receptionist(ObjectId id, String username, String password, List<UserRole> roles) {
        super(id, username, password, roles);
    }
}
