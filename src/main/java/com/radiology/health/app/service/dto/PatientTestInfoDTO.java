package com.radiology.health.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.radiology.health.app.domain.PatientTestInfo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PatientTestInfoDTO implements Serializable {

    private Long id;

    private PatientInfoDTO patientInfo;

    private TestTimingsDTO testTimings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PatientInfoDTO getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(PatientInfoDTO patientInfo) {
        this.patientInfo = patientInfo;
    }

    public TestTimingsDTO getTestTimings() {
        return testTimings;
    }

    public void setTestTimings(TestTimingsDTO testTimings) {
        this.testTimings = testTimings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientTestInfoDTO)) {
            return false;
        }

        PatientTestInfoDTO patientTestInfoDTO = (PatientTestInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, patientTestInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientTestInfoDTO{" +
            "id=" + getId() +
            ", patientInfo=" + getPatientInfo() +
            ", testTimings=" + getTestTimings() +
            "}";
    }
}
