package com.radiology.health.care.service.mapper;

import com.radiology.health.care.domain.PatientInfo;
import com.radiology.health.care.domain.PatientTestTimings;
import com.radiology.health.care.domain.TestCategories;
import com.radiology.health.care.service.dto.PatientInfoDTO;
import com.radiology.health.care.service.dto.PatientTestTimingsDTO;
import com.radiology.health.care.service.dto.TestCategoriesDTO;
import com.radiology.health.care.web.rest.TestCategoriesResource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PatientTestTimings} and its DTO {@link PatientTestTimingsDTO}.
 */
@Mapper(componentModel = "spring")
public interface PatientTestTimingsMapper extends EntityMapper<PatientTestTimingsDTO, PatientTestTimings> {
    @Mapping(target = "patientInfoId", source = "patientInfo.id")
    @Mapping(target = "testCategoriesId", source = "testCategories.id")
    PatientTestTimingsDTO toDto(PatientTestTimings s);

    @Mapping(target = "patientInfo.id", source = "patientInfoId")
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
        ZonedDateTime startTime = dto.getStartTime();
        ZonedDateTime endTime = dto.getEndTime();

        patientTestTimings.setId(dto.getId());
        patientTestTimings.setPriority(dto.getPriority());
        patientTestTimings.setClinicalNote(dto.getClinicalNote());
        patientTestTimings.setSpclInstruction(dto.getSpclInstruction());
        patientTestTimings.setStatus(dto.getStatus());
        patientTestTimings.setRecommendedDoctor(dto.getRecommendedDoctor());
        patientTestTimings.setStartTime(startTime);
        patientTestTimings.setEndTime(endTime);
        patientTestTimings.setLogin(dto.getLogin());
        patientTestTimings.setCreatedBy(dto.getCreatedBy());
        patientTestTimings.setCreatedDate(dto.getCreatedDate());
        patientTestTimings.setLastModifiedBy(dto.getLastModifiedBy());
        patientTestTimings.setLastModifiedDate(dto.getLastModifiedDate());
        if (dto.getPatientReport() != null) {
            patientTestTimings.setPatientReport(dto.getPatientReport());
        }
        patientTestTimings.patientInfo(patientInfo);
        patientTestTimings.testCategories(testCategories);

        return patientTestTimings;
    }

    default void partialUpdate(PatientTestTimings entity, PatientTestTimingsDTO dto) {
        if (dto == null) {
            return;
        }

        if (dto.getCreatedBy() != null) {
            entity.setCreatedBy(dto.getCreatedBy());
        }
        if (dto.getCreatedDate() != null) {
            entity.setCreatedDate(dto.getCreatedDate());
        }
        if (dto.getLastModifiedBy() != null) {
            entity.setLastModifiedBy(dto.getLastModifiedBy());
        }
        if (dto.getLastModifiedDate() != null) {
            entity.setLastModifiedDate(dto.getLastModifiedDate());
        }
        if (dto.getLogin() != null) {
            entity.setLogin(dto.getLogin());
        }
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getPriority() != null) {
            entity.setPriority(dto.getPriority());
        }
        if (dto.getClinicalNote() != null) {
            entity.setClinicalNote(dto.getClinicalNote());
        }
        if (dto.getRecommendedDoctor() != null) {
            entity.setRecommendedDoctor(dto.getRecommendedDoctor());
        }
        if (dto.getSpclInstruction() != null) {
            entity.setSpclInstruction(dto.getSpclInstruction());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getPatientReport() != null) {
            entity.setPatientReport(dto.getPatientReport());
        }

        if (dto.getStartTime() != null) {
            ZonedDateTime startTime = dto.getStartTime();
            entity.setStartTime(startTime);
        }
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
