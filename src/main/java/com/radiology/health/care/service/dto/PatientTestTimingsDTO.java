package com.radiology.health.care.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.radiology.health.care.domain.PatientTestTimings} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PatientTestTimingsDTO implements Serializable {

    private Long id;

    private LocalDate testTimings;

    private String priority;

    private String clinicalNote;

    private String spclInstruction;


    public PatientTestTimingsDTO status(String status) {
        this.status = status;
        return this;
    }

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private Long patientInfoId;


    private TestCategoriesDTO testCategories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTestTimings() {
        return testTimings;
    }

    public void setTestTimings(LocalDate testTimings) {
        this.testTimings = testTimings;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getClinicalNote() {
        return clinicalNote;
    }

    public void setClinicalNote(String clinicalNote) {
        this.clinicalNote = clinicalNote;
    }

    public String getSpclInstruction() {
        return spclInstruction;
    }

    public void setSpclInstruction(String spclInstruction) {
        this.spclInstruction = spclInstruction;
    }

    public PatientInfoDTO getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(PatientInfoDTO patientInfo) {
        this.patientInfo = patientInfo;
    }

    public TestCategoriesDTO getTestCategories() {
        return testCategories;
    }

    public void setTestCategories(TestCategoriesDTO testCategories) {
        this.testCategories = testCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientTestTimingsDTO)) {
            return false;
        }

        PatientTestTimingsDTO patientTestTimingsDTO = (PatientTestTimingsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, patientTestTimingsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientTestTimingsDTO{" +
            "id=" + getId() +
            ", testTimings='" + getTestTimings() + "'" +
            ", priority='" + getPriority() + "'" +
            ", clinicalNote='" + getClinicalNote() + "'" +
            ", spclInstruction='" + getSpclInstruction() + "'" +
            ", status='" + getStatus() + "'" +
            ", patientInfoId=" + getPatientInfoId() +
            ", testCategoriesId=" + getTestCategoriesId() +

            "}";
    }
}
