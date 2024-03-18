package com.radiology.health.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Equipments.
 */
@Entity
@Table(name = "equipments")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Equipments implements Serializable {

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
    @JsonIgnoreProperties(value = { "equipments" }, allowSetters = true)
    private Rooms rooms;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "equipments")
    @JsonIgnoreProperties(
        value = { "equipments", "testCatogories_parent", "test_catogories_parent_catogories", "testTimings" },
        allowSetters = true
    )
    private Set<TestCatogories> testCatogories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "equipments")
    @JsonIgnoreProperties(value = { "employee", "equipments" }, allowSetters = true)
    private Set<TechicianEquipmentMapping> techicianEquipmentMappings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Equipments id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Equipments name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rooms getRooms() {
        return this.rooms;
    }

    public void setRooms(Rooms rooms) {
        this.rooms = rooms;
    }

    public Equipments rooms(Rooms rooms) {
        this.setRooms(rooms);
        return this;
    }

    public Set<TestCatogories> getTestCatogories() {
        return this.testCatogories;
    }

    public void setTestCatogories(Set<TestCatogories> testCatogories) {
        if (this.testCatogories != null) {
            this.testCatogories.forEach(i -> i.setEquipments(null));
        }
        if (testCatogories != null) {
            testCatogories.forEach(i -> i.setEquipments(this));
        }
        this.testCatogories = testCatogories;
    }

    public Equipments testCatogories(Set<TestCatogories> testCatogories) {
        this.setTestCatogories(testCatogories);
        return this;
    }

    public Equipments addTestCatogories(TestCatogories testCatogories) {
        this.testCatogories.add(testCatogories);
        testCatogories.setEquipments(this);
        return this;
    }

    public Equipments removeTestCatogories(TestCatogories testCatogories) {
        this.testCatogories.remove(testCatogories);
        testCatogories.setEquipments(null);
        return this;
    }

    public Set<TechicianEquipmentMapping> getTechicianEquipmentMappings() {
        return this.techicianEquipmentMappings;
    }

    public void setTechicianEquipmentMappings(Set<TechicianEquipmentMapping> techicianEquipmentMappings) {
        if (this.techicianEquipmentMappings != null) {
            this.techicianEquipmentMappings.forEach(i -> i.setEquipments(null));
        }
        if (techicianEquipmentMappings != null) {
            techicianEquipmentMappings.forEach(i -> i.setEquipments(this));
        }
        this.techicianEquipmentMappings = techicianEquipmentMappings;
    }

    public Equipments techicianEquipmentMappings(Set<TechicianEquipmentMapping> techicianEquipmentMappings) {
        this.setTechicianEquipmentMappings(techicianEquipmentMappings);
        return this;
    }

    public Equipments addTechicianEquipmentMapping(TechicianEquipmentMapping techicianEquipmentMapping) {
        this.techicianEquipmentMappings.add(techicianEquipmentMapping);
        techicianEquipmentMapping.setEquipments(this);
        return this;
    }

    public Equipments removeTechicianEquipmentMapping(TechicianEquipmentMapping techicianEquipmentMapping) {
        this.techicianEquipmentMappings.remove(techicianEquipmentMapping);
        techicianEquipmentMapping.setEquipments(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Equipments)) {
            return false;
        }
        return getId() != null && getId().equals(((Equipments) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Equipments{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
