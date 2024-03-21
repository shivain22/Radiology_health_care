package com.radiology.health.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A TechnicianEquipmentMapping.
 */
@Entity
@Table(name = "technician_equipment_mapping")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TechnicianEquipmentMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date_time", nullable = false)
    private Instant dateTime;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "room", "technicianEquipmentMappings", "testCategories" }, allowSetters = true)
    private Equipment equipment;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "rank",
            "empService",
            "unit",
            "technicianEquipmentMappings",
            "patientInfoEmployeeIds",
            "patientInfoEmployeeHis",
            "patientInfoEmployeeServiceNos",
        },
        allowSetters = true
    )
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TechnicianEquipmentMapping id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateTime() {
        return this.dateTime;
    }

    public TechnicianEquipmentMapping dateTime(Instant dateTime) {
        this.setDateTime(dateTime);
        return this;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    public Equipment getEquipment() {
        return this.equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public TechnicianEquipmentMapping equipment(Equipment equipment) {
        this.setEquipment(equipment);
        return this;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public TechnicianEquipmentMapping employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TechnicianEquipmentMapping)) {
            return false;
        }
        return getId() != null && getId().equals(((TechnicianEquipmentMapping) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TechnicianEquipmentMapping{" +
            "id=" + getId() +
            ", dateTime='" + getDateTime() + "'" +
            "}";
    }
}
