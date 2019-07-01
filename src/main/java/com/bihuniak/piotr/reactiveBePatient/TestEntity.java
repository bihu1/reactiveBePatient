package com.bihuniak.piotr.reactiveBePatient;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class TestEntity {

    @Id
    ObjectId id = new ObjectId();

    String name;
}
