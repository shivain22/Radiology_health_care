package com.radiology.health.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TestCategories.
 */
@Entity
@Table(name = "test_categories")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TestCategories implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "test_name", nullable = false)
    private String testName;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "room", "technicianEquipmentMappings", "testCategories" }, allowSetters = true)
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "equipment", "parentTestCategory", "patientTestTimings", "testCategoryParents" }, allowSetters = true)
    private TestCategories parentTestCategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "testCategories")
    @JsonIgnoreProperties(value = { "patientInfo", "testCategories" }, allowSetters = true)
    private Set<PatientTestTimings> patientTestTimings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentTestCategory")
    @JsonIgnoreProperties(value = { "equipment", "parentTestCategory", "patientTestTimings", "testCategoryParents" }, allowSetters = true)
    private Set<TestCategories> testCategoryParents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TestCategories id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestName() {
        return this.testName;
    }

    public TestCategories testName(String testName) {
        this.setTestName(testName);
        return this;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Equipment getEquipment() {
        return this.equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public TestCategories equipment(Equipment equipment) {
        this.setEquipment(equipment);
        return this;
    }

    public TestCategories getParentTestCategory() {
        return this.parentTestCategory;
    }

    public void setParentTestCategory(TestCategories testCategories) {
        this.parentTestCategory = testCategories;
    }

    public TestCategories parentTestCategory(TestCategories testCategories) {
        this.setParentTestCategory(testCategories);
        return this;
    }

    public Set<PatientTestTimings> getPatientTestTimings() {
        return this.patientTestTimings;
    }

    public void setPatientTestTimings(Set<PatientTestTimings> patientTestTimings) {
        if (this.patientTestTimings != null) {
            this.patientTestTimings.forEach(i -> i.setTestCategories(null));
        }
        if (patientTestTimings != null) {
            patientTestTimings.forEach(i -> i.setTestCategories(this));
        }
        this.patientTestTimings = patientTestTimings;
    }

    public TestCategories patientTestTimings(Set<PatientTestTimings> patientTestTimings) {
        this.setPatientTestTimings(patientTestTimings);
        return this;
    }

    public TestCategories addPatientTestTimings(PatientTestTimings patientTestTimings) {
        this.patientTestTimings.add(patientTestTimings);
        patientTestTimings.setTestCategories(this);
        return this;
    }

    public TestCategories removePatientTestTimings(PatientTestTimings patientTestTimings) {
        this.patientTestTimings.remove(patientTestTimings);
        patientTestTimings.setTestCategories(null);
        return this;
    }

    public Set<TestCategories> getTestCategoryParents() {
        return this.testCategoryParents;
    }

    public void setTestCategoryParents(Set<TestCategories> testCategories) {
        if (this.testCategoryParents != null) {
            this.testCategoryParents.forEach(i -> i.setParentTestCategory(null));
        }
        if (testCategories != null) {
            testCategories.forEach(i -> i.setParentTestCategory(this));
        }
        this.testCategoryParents = testCategories;
    }

    public TestCategories testCategoryParents(Set<TestCategories> testCategories) {
        this.setTestCategoryParents(testCategories);
        return this;
    }

    public TestCategories addTestCategoryParent(TestCategories testCategories) {
        this.testCategoryParents.add(testCategories);
        testCategories.setParentTestCategory(this);
        return this;
    }

    public TestCategories removeTestCategoryParent(TestCategories testCategories) {
        this.testCategoryParents.remove(testCategories);
        testCategories.setParentTestCategory(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TestCategories)) {
            return false;
        }
        return getId() != null && getId().equals(((TestCategories) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TestCategories{" +
            "id=" + getId() +
            ", testName='" + getTestName() + "'" +
            "}";
    }
}
