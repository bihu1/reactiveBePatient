package com.bihuniak.piotr.reactiveBePatient;

import javax.validation.constraints.Pattern;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Pattern(regexp = "/^[a-f\\d]{24}$/i")
@Retention(RetentionPolicy.RUNTIME)
public @interface ObjectIdValid {
}
