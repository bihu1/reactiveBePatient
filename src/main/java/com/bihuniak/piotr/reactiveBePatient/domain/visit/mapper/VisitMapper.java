package com.bihuniak.piotr.reactiveBePatient.domain.visit.mapper;

import com.dryPepperoniStickTeam.bePatient.config.orika.Mapping;
import com.dryPepperoniStickTeam.bePatient.config.orika.OrikaBeanMapping;
import com.dryPepperoniStickTeam.bePatient.domain.visit.Visit;
import com.dryPepperoniStickTeam.bePatient.domain.visit.http.model.ReservedVisitView;
import com.dryPepperoniStickTeam.bePatient.domain.visit.http.model.VisitView;
import ma.glasnost.orika.MapperFactory;

@Mapping
public class VisitMapper implements OrikaBeanMapping {
    @Override
    public void configure(MapperFactory mapperFactory) {
        mapperFactory.classMap(Visit.class, ReservedVisitView.class)
                .field("doctor.firstName","doctorFirstName")
                .field("doctor.lastName","doctorLastName")
                .field("dateFrom","date")
                .field("patient.id","patientId")
                .byDefault()
                .register();

        mapperFactory.classMap(Visit.class, VisitView.class)
                .field("patient.id","patientId")
                .byDefault()
                .register();
    }
}
