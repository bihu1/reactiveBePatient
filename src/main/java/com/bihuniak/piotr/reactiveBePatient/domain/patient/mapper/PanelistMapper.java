package com.bihuniak.piotr.reactiveBePatient.domain.patient.mapper;

import com.bihuniak.piotr.reactiveBePatient.config.orika.Mapping;
import com.bihuniak.piotr.reactiveBePatient.config.orika.OrikaBeanMapping;
import com.bihuniak.piotr.reactiveBePatient.domain.patient.http.model.PatientDetails;
import com.bihuniak.piotr.reactiveBePatient.domain.patient.http.model.PatientView;
import com.bihuniak.piotr.reactiveBePatient.domain.patient.model.Patient;
import ma.glasnost.orika.MapperFactory;

@Mapping
public class PanelistMapper implements OrikaBeanMapping {
    @Override
    public void configure(MapperFactory mapperFactory) {
        mapperFactory.classMap(PatientDetails.class, Patient.class)
                .field("pesel","username")
                .byDefault()
                .register();
        mapperFactory.classMap(Patient.class, PatientView.class)
                .field("username","pesel")
                .byDefault()
                .register();
    }
}
