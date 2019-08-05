package com.bihuniak.piotr.reactiveBePatient.common;

import com.bihuniak.piotr.reactiveBePatient.ObjectIdValid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class IdHolder {

    @NotNull
    @ObjectIdValid
    String id;
}
