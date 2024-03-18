package com.radiology.health.app.service.mapper;

import com.radiology.health.app.domain.PatientInfo;
import com.radiology.health.app.domain.PatientTestInfo;
import com.radiology.health.app.domain.TestTimings;
import com.radiology.health.app.service.dto.PatientInfoDTO;
import com.radiology.health.app.service.dto.PatientTestInfoDTO;
import com.radiology.health.app.service.dto.TestTimingsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PatientTestInfo} and its DTO {@link PatientTestInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PatientTestInfoMapper extends EntityMapper<PatientTestInfoDTO, PatientTestInfo> {
    @Mapping(target = "patientInfo", source = "patientInfo", qualifiedByName = "patientInfoId")
    @Mapping(target = "testTimings", source = "testTimings", qualifiedByName = "testTimingsId")
    PatientTestInfoDTO toDto(PatientTestInfo s);

    @Named("patientInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PatientInfoDTO toDtoPatientInfoId(PatientInfo patientInfo);

    @Named("testTimingsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TestTimingsDTO toDtoTestTimingsId(TestTimings testTimings);
}
