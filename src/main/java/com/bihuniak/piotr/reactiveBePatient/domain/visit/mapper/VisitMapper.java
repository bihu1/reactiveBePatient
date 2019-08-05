package com.bihuniak.piotr.reactiveBePatient.domain.visit.mapper;

import com.bihuniak.piotr.reactiveBePatient.config.orika.Mapping;
import com.bihuniak.piotr.reactiveBePatient.config.orika.OrikaBeanMapping;
import com.bihuniak.piotr.reactiveBePatient.domain.visit.Visit;
import com.bihuniak.piotr.reactiveBePatient.domain.visit.http.model.ReservedVisitView;
import com.bihuniak.piotr.reactiveBePatient.domain.visit.http.model.VisitView;
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
