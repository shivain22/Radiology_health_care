package com.radiology.health.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TestTimings.
 */
@Entity
@Table(name = "test_timings")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TestTimings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "timings", nullable = false)
    private String timings;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "equipments", "testCatogories_parent", "test_catogories_parent_catogories", "testTimings" },
        allowSetters = true
    )
    private TestCatogories testCatogories;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "testTimings")
    @JsonIgnoreProperties(value = { "patientInfo", "testTimings" }, allowSetters = true)
    private Set<PatientTestInfo> patientTestInfos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TestTimings id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimings() {
        return this.timings;
    }

    public TestTimings timings(String timings) {
        this.setTimings(timings);
        return this;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public TestCatogories getTestCatogories() {
        return this.testCatogories;
    }

    public void setTestCatogories(TestCatogories testCatogories) {
        this.testCatogories = testCatogories;
    }

    public TestTimings testCatogories(TestCatogories testCatogories) {
        this.setTestCatogories(testCatogories);
        return this;
    }

    public Set<PatientTestInfo> getPatientTestInfos() {
        return this.patientTestInfos;
    }

    public void setPatientTestInfos(Set<PatientTestInfo> patientTestInfos) {
        if (this.patientTestInfos != null) {
            this.patientTestInfos.forEach(i -> i.setTestTimings(null));
        }
        if (patientTestInfos != null) {
            patientTestInfos.forEach(i -> i.setTestTimings(this));
        }
        this.patientTestInfos = patientTestInfos;
    }

    public TestTimings patientTestInfos(Set<PatientTestInfo> patientTestInfos) {
        this.setPatientTestInfos(patientTestInfos);
        return this;
    }

    public TestTimings addPatientTestInfo(PatientTestInfo patientTestInfo) {
        this.patientTestInfos.add(patientTestInfo);
        patientTestInfo.setTestTimings(this);
        return this;
    }

    public TestTimings removePatientTestInfo(PatientTestInfo patientTestInfo) {
        this.patientTestInfos.remove(patientTestInfo);
        patientTestInfo.setTestTimings(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TestTimings)) {
            return false;
        }
        return getId() != null && getId().equals(((TestTimings) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TestTimings{" +
            "id=" + getId() +
            ", timings='" + getTimings() + "'" +
            "}";
    }
}
