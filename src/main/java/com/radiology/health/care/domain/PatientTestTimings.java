package com.radiology.health.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.envers.Audited;

/**
 * A PatientTestTimings.
 */
@Entity
@Table(name = "patient_test_timings")
@Audited
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PatientTestTimings extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "test_timings")
    private LocalDate testTimings;

    @Column(name = "priority")
    private String priority;

    @Column(name = "clinical_note")
    private String clinicalNote;

    @Column(name = "spcl_instruction")
    private String spclInstruction;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "status")
    private String status;

    public PatientTestTimings status(String status) {
        this.status = status;
        return this;
    }

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "employeeId", "employeeHis", "employeeServiceNo", "patientTestTimings" }, allowSetters = true)
    private PatientInfo patientInfo;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    private String login;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "equipment", "parentTestCategory", "user", "patientTestTimings", "testCategoryParents" },
        allowSetters = true
    )
    private TestCategories testCategories;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PatientTestTimings id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTestTimings() {
        return this.testTimings;
    }

    public PatientTestTimings testTimings(LocalDate testTimings) {
        this.setTestTimings(testTimings);
        return this;
    }

    public void setTestTimings(LocalDate testTimings) {
        this.testTimings = testTimings;
    }

    public String getPriority() {
        return this.priority;
    }

    public PatientTestTimings priority(String priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getClinicalNote() {
        return this.clinicalNote;
    }

    public PatientTestTimings clinicalNote(String clinicalNote) {
        this.setClinicalNote(clinicalNote);
        return this;
    }

    public void setClinicalNote(String clinicalNote) {
        this.clinicalNote = clinicalNote;
    }

    public String getSpclInstruction() {
        return this.spclInstruction;
    }

    public PatientTestTimings spclInstruction(String spclInstruction) {
        this.setSpclInstruction(spclInstruction);
        return this;
    }

    public void setSpclInstruction(String spclInstruction) {
        this.spclInstruction = spclInstruction;
    }

    public PatientInfo getPatientInfo() {
        return this.patientInfo;
    }

    public void setPatientInfo(PatientInfo patientInfo) {
        this.patientInfo = patientInfo;
    }

    public PatientTestTimings patientInfo(PatientInfo patientInfo) {
        this.setPatientInfo(patientInfo);
        return this;
    }

    public TestCategories getTestCategories() {
        return this.testCategories;
    }

    public void setTestCategories(TestCategories testCategories) {
        this.testCategories = testCategories;
    }

    public PatientTestTimings testCategories(TestCategories testCategories) {
        this.setTestCategories(testCategories);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientTestTimings)) {
            return false;
        }
        return getId() != null && getId().equals(((PatientTestTimings) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientTestTimings{" +
            "id=" + getId() +
            ", testTimings='" + getTestTimings() + "'" +
            ", priority='" + getPriority() + "'" +
            ", clinicalNote='" + getClinicalNote() + "'" +
            ", spclInstruction='" + getSpclInstruction() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
