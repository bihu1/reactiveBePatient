package com.bihuniak.piotr.reactiveBePatient.domain.disease;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Disease {

    @Id
    ObjectId id;

    @CsvBindByPosition(position = 0)
    String name;
}
