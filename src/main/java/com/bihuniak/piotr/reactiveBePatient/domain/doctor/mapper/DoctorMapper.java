package com.bihuniak.piotr.reactiveBePatient.domain.doctor.mapper;

import com.dryPepperoniStickTeam.bePatient.config.orika.Mapping;
import com.dryPepperoniStickTeam.bePatient.config.orika.OrikaBeanMapping;
import com.dryPepperoniStickTeam.bePatient.domain.doctor.Doctor;
import com.dryPepperoniStickTeam.bePatient.domain.doctor.http.model.DoctorView;
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
