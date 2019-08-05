package com.bihuniak.piotr.reactiveBePatient.domain.doctor.mapper;

import com.bihuniak.piotr.reactiveBePatient.config.orika.Mapping;
import com.bihuniak.piotr.reactiveBePatient.config.orika.OrikaBeanMapping;
import com.bihuniak.piotr.reactiveBePatient.domain.doctor.Doctor;
import com.bihuniak.piotr.reactiveBePatient.domain.doctor.http.model.DoctorView;
import ma.glasnost.orika.MapperFactory;

@Mapping
public class DoctorMapper implements OrikaBeanMapping {
    @Override
    public void configure(MapperFactory mapperFactory) {

        mapperFactory.classMap(Doctor.class, DoctorView.class)
                .field("medicalServices","services")
                .byDefault()
                .register();
    }
}
