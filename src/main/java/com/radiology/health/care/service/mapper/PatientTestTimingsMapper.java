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
    @Mapping(target = "patientInfoId", source = "patientInfo.id")
    @Mapping(target = "testCategoriesId", source = "testCategories.id")
    PatientTestTimingsDTO toDto(PatientTestTimings s);

    @Mapping(target = "patientInfo.id", source = "patientInfo.id")
    @Mapping(target = "testCategories.id", source = "testCategoriesId")
    default PatientTestTimings toEntity(PatientTestTimingsDTO dto) {
        if (dto == null) {
            return null;
        }

        PatientTestTimings patientTestTimings = new PatientTestTimings();

        PatientInfo patientInfo = new PatientInfo();
        TestCategories testCategories = new TestCategories();

        patientInfo.setId(dto.getPatientInfoId());
        testCategories.setId(dto.getTestCategoriesId());

        patientTestTimings.setId(dto.getId());
        patientTestTimings.setTestTimings(dto.getTestTimings());
        patientTestTimings.setPriority(dto.getPriority());
        patientTestTimings.setClinicalNote(dto.getClinicalNote());
        patientTestTimings.setSpclInstruction(dto.getSpclInstruction());
        patientTestTimings.patientInfo(patientInfo);
        patientTestTimings.testCategories(testCategories);
        patientTestTimings.setLogin(dto.getLogin());
        patientTestTimings.setCreatedBy(dto.getCreatedBy());
        patientTestTimings.setCreatedDate(dto.getCreatedDate());
        patientTestTimings.setLastModifiedBy(dto.getLastModifiedBy());
        patientTestTimings.setLastModifiedDate(dto.getLastModifiedDate());

        return patientTestTimings;
    }

    @Named("patientInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PatientInfoDTO toDtoPatientInfoId(PatientInfo patientInfo);

    @Named("testCategoriesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TestCategoriesDTO toDtoTestCategoriesId(TestCategories testCategories);
}
