package com.radiology.health.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Equipment.
 */
@Entity
@Table(name = "equipment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Equipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user", "equipment" }, allowSetters = true)
    private Room room;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "equipment")
    @JsonIgnoreProperties(value = { "equipment", "employee", "user" }, allowSetters = true)
    private Set<TechnicianEquipmentMapping> technicianEquipmentMappings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "equipment")
    @JsonIgnoreProperties(
        value = { "equipment", "parentTestCategory", "user", "patientTestTimings", "testCategoryParents" },
        allowSetters = true
    )
    private Set<TestCategories> testCategories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Equipment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Equipment name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Equipment room(Room room) {
        this.setRoom(room);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Equipment user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<TechnicianEquipmentMapping> getTechnicianEquipmentMappings() {
        return this.technicianEquipmentMappings;
    }

    public void setTechnicianEquipmentMappings(Set<TechnicianEquipmentMapping> technicianEquipmentMappings) {
        if (this.technicianEquipmentMappings != null) {
            this.technicianEquipmentMappings.forEach(i -> i.setEquipment(null));
        }
        if (technicianEquipmentMappings != null) {
            technicianEquipmentMappings.forEach(i -> i.setEquipment(this));
        }
        this.technicianEquipmentMappings = technicianEquipmentMappings;
    }

    public Equipment technicianEquipmentMappings(Set<TechnicianEquipmentMapping> technicianEquipmentMappings) {
        this.setTechnicianEquipmentMappings(technicianEquipmentMappings);
        return this;
    }

    public Equipment addTechnicianEquipmentMapping(TechnicianEquipmentMapping technicianEquipmentMapping) {
        this.technicianEquipmentMappings.add(technicianEquipmentMapping);
        technicianEquipmentMapping.setEquipment(this);
        return this;
    }

    public Equipment removeTechnicianEquipmentMapping(TechnicianEquipmentMapping technicianEquipmentMapping) {
        this.technicianEquipmentMappings.remove(technicianEquipmentMapping);
        technicianEquipmentMapping.setEquipment(null);
        return this;
    }

    public Set<TestCategories> getTestCategories() {
        return this.testCategories;
    }

    public void setTestCategories(Set<TestCategories> testCategories) {
        if (this.testCategories != null) {
            this.testCategories.forEach(i -> i.setEquipment(null));
        }
        if (testCategories != null) {
            testCategories.forEach(i -> i.setEquipment(this));
        }
        this.testCategories = testCategories;
    }

    public Equipment testCategories(Set<TestCategories> testCategories) {
        this.setTestCategories(testCategories);
        return this;
    }

    public Equipment addTestCategories(TestCategories testCategories) {
        this.testCategories.add(testCategories);
        testCategories.setEquipment(this);
        return this;
    }

    public Equipment removeTestCategories(TestCategories testCategories) {
        this.testCategories.remove(testCategories);
        testCategories.setEquipment(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Equipment)) {
            return false;
        }
        return getId() != null && getId().equals(((Equipment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Equipment{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
