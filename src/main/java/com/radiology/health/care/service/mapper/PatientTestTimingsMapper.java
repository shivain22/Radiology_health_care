package com.radiology.health.care.service.mapper;

import com.radiology.health.care.domain.PatientInfo;
import com.radiology.health.care.domain.PatientTestTimings;
import com.radiology.health.care.domain.TestCategories;
import com.radiology.health.care.service.dto.PatientInfoDTO;
import com.radiology.health.care.service.dto.PatientTestTimingsDTO;
import com.radiology.health.care.service.dto.TestCategoriesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PatientTestTimings} and its DTO {@link PatientTestTimingsDTO}.
 */
@Mapper(componentModel = "spring")
public interface PatientTestTimingsMapper extends EntityMapper<PatientTestTimingsDTO, PatientTestTimings> {
    @Mapping(target = "patientInfo", source = "patientInfo", qualifiedByName = "patientInfoId")
    @Mapping(target = "testCategories", source = "testCategories", qualifiedByName = "testCategoriesId")
    PatientTestTimingsDTO toDto(PatientTestTimings s);

    @Named("patientInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PatientInfoDTO toDtoPatientInfoId(PatientInfo patientInfo);

    @Named("testCategoriesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TestCategoriesDTO toDtoTestCategoriesId(TestCategories testCategories);
}
