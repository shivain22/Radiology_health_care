package com.radiology.health.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A PatientTestInfo.
 */
@Entity
@Table(name = "patient_test_info")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PatientTestInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "employee", "patientTestInfos" }, allowSetters = true)
    private PatientInfo patientInfo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "testCatogories", "patientTestInfos" }, allowSetters = true)
    private TestTimings testTimings;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PatientTestInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PatientInfo getPatientInfo() {
        return this.patientInfo;
    }

    public void setPatientInfo(PatientInfo patientInfo) {
        this.patientInfo = patientInfo;
    }

    public PatientTestInfo patientInfo(PatientInfo patientInfo) {
        this.setPatientInfo(patientInfo);
        return this;
    }

    public TestTimings getTestTimings() {
        return this.testTimings;
    }

    public void setTestTimings(TestTimings testTimings) {
        this.testTimings = testTimings;
    }

    public PatientTestInfo testTimings(TestTimings testTimings) {
        this.setTestTimings(testTimings);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientTestInfo)) {
            return false;
        }
        return getId() != null && getId().equals(((PatientTestInfo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientTestInfo{" +
            "id=" + getId() +
            "}";
    }
}
