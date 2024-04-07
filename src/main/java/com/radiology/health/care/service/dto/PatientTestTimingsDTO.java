package com.radiology.health.care.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.radiology.health.care.domain.PatientTestTimings} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PatientTestTimingsDTO implements Serializable {

    private Long id;

    private String priority;

    private String clinicalNote;

    private String spclInstruction;

    private String status;

    private ZonedDateTime startTiming;

    private ZonedDateTime endTime;

    private Long patientInfoId;

    private Long testCategoriesId;

    private String login;
    private String createdBy;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getStartTiming() {
        return startTiming;
    }

    public void setStartTiming(ZonedDateTime startTiming) {
        this.startTiming = startTiming;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getPatientInfoId() {
        return patientInfoId;
    }

    public void setPatientInfoId(Long patientInfoId) {
        this.patientInfoId = patientInfoId;
    }

    public Long getTestCategoriesId() {
        return testCategoriesId;
    }

    public void setTestCategoriesId(Long testCategoriesId) {
        this.testCategoriesId = testCategoriesId;
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
            ", priority='" + getPriority() + "'" +
            ", clinicalNote='" + getClinicalNote() + "'" +
            ", spclInstruction='" + getSpclInstruction() + "'" +
            ", status='" + getStatus() + "'" +
            ", startTiming='" + getStartTiming() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", patientInfoId=" + getPatientInfoId() +
            ", testCategoriesId=" + getTestCategoriesId() +
            "}";
    }
}
