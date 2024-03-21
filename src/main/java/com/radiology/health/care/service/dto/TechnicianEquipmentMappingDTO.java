package com.radiology.health.care.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.radiology.health.care.domain.TechnicianEquipmentMapping} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TechnicianEquipmentMappingDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant dateTime;

    private EquipmentDTO equipment;

    private EmployeeDTO employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    public EquipmentDTO getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentDTO equipment) {
        this.equipment = equipment;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TechnicianEquipmentMappingDTO)) {
            return false;
        }

        TechnicianEquipmentMappingDTO technicianEquipmentMappingDTO = (TechnicianEquipmentMappingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, technicianEquipmentMappingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TechnicianEquipmentMappingDTO{" +
            "id=" + getId() +
            ", dateTime='" + getDateTime() + "'" +
            ", equipment=" + getEquipment() +
            ", employee=" + getEmployee() +
            "}";
    }
}
