package com.radiology.health.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A TechicianEquipmentMapping.
 */
@Entity
@Table(name = "techician_equipment_mapping")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TechicianEquipmentMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date_time", nullable = false)
    private String dateTime;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "unit", "services", "rank", "patientInfos", "techicianEquipmentMappings" }, allowSetters = true)
    private Employee employee;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "rooms", "testCatogories", "techicianEquipmentMappings" }, allowSetters = true)
    private Equipments equipments;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TechicianEquipmentMapping id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public TechicianEquipmentMapping dateTime(String dateTime) {
        this.setDateTime(dateTime);
        return this;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public TechicianEquipmentMapping employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    public Equipments getEquipments() {
        return this.equipments;
    }

    public void setEquipments(Equipments equipments) {
        this.equipments = equipments;
    }

    public TechicianEquipmentMapping equipments(Equipments equipments) {
        this.setEquipments(equipments);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TechicianEquipmentMapping)) {
            return false;
        }
        return getId() != null && getId().equals(((TechicianEquipmentMapping) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TechicianEquipmentMapping{" +
            "id=" + getId() +
            ", dateTime='" + getDateTime() + "'" +
            "}";
    }
}
