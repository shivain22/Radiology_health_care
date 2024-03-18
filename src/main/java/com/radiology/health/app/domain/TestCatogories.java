package com.radiology.health.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TestCatogories.
 */
@Entity
@Table(name = "test_catogories")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TestCatogories implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "rooms", "testCatogories", "techicianEquipmentMappings" }, allowSetters = true)
    private Equipments equipments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "equipments", "testCatogories_parent", "test_catogories_parent_catogories", "testTimings" },
        allowSetters = true
    )
    private TestCatogories testCatogories_parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "testCatogories_parent")
    @JsonIgnoreProperties(
        value = { "equipments", "testCatogories_parent", "test_catogories_parent_catogories", "testTimings" },
        allowSetters = true
    )
    private Set<TestCatogories> test_catogories_parent_catogories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "testCatogories")
    @JsonIgnoreProperties(value = { "testCatogories", "patientTestInfos" }, allowSetters = true)
    private Set<TestTimings> testTimings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TestCatogories id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public TestCatogories name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Equipments getEquipments() {
        return this.equipments;
    }

    public void setEquipments(Equipments equipments) {
        this.equipments = equipments;
    }

    public TestCatogories equipments(Equipments equipments) {
        this.setEquipments(equipments);
        return this;
    }

    public TestCatogories getTestCatogories_parent() {
        return this.testCatogories_parent;
    }

    public void setTestCatogories_parent(TestCatogories testCatogories) {
        this.testCatogories_parent = testCatogories;
    }

    public TestCatogories testCatogories_parent(TestCatogories testCatogories) {
        this.setTestCatogories_parent(testCatogories);
        return this;
    }

    public Set<TestCatogories> getTest_catogories_parent_catogories() {
        return this.test_catogories_parent_catogories;
    }

    public void setTest_catogories_parent_catogories(Set<TestCatogories> testCatogories) {
        if (this.test_catogories_parent_catogories != null) {
            this.test_catogories_parent_catogories.forEach(i -> i.setTestCatogories_parent(null));
        }
        if (testCatogories != null) {
            testCatogories.forEach(i -> i.setTestCatogories_parent(this));
        }
        this.test_catogories_parent_catogories = testCatogories;
    }

    public TestCatogories test_catogories_parent_catogories(Set<TestCatogories> testCatogories) {
        this.setTest_catogories_parent_catogories(testCatogories);
        return this;
    }

    public TestCatogories addTest_catogories_parent_catogory(TestCatogories testCatogories) {
        this.test_catogories_parent_catogories.add(testCatogories);
        testCatogories.setTestCatogories_parent(this);
        return this;
    }

    public TestCatogories removeTest_catogories_parent_catogory(TestCatogories testCatogories) {
        this.test_catogories_parent_catogories.remove(testCatogories);
        testCatogories.setTestCatogories_parent(null);
        return this;
    }

    public Set<TestTimings> getTestTimings() {
        return this.testTimings;
    }

    public void setTestTimings(Set<TestTimings> testTimings) {
        if (this.testTimings != null) {
            this.testTimings.forEach(i -> i.setTestCatogories(null));
        }
        if (testTimings != null) {
            testTimings.forEach(i -> i.setTestCatogories(this));
        }
        this.testTimings = testTimings;
    }

    public TestCatogories testTimings(Set<TestTimings> testTimings) {
        this.setTestTimings(testTimings);
        return this;
    }

    public TestCatogories addTestTimings(TestTimings testTimings) {
        this.testTimings.add(testTimings);
        testTimings.setTestCatogories(this);
        return this;
    }

    public TestCatogories removeTestTimings(TestTimings testTimings) {
        this.testTimings.remove(testTimings);
        testTimings.setTestCatogories(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TestCatogories)) {
            return false;
        }
        return getId() != null && getId().equals(((TestCatogories) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TestCatogories{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
