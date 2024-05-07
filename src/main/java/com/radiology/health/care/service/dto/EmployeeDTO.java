package com.radiology.health.care.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.radiology.health.care.domain.Employee} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeDTO implements Serializable {

    private Long id;

    private String name;

    private Boolean technician;

    private String his;

    private String serviceNo;

    private RankDTO rank;

    private EmpServiceDTO empService;

    private UnitDTO unit;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getTechnician() {
        return technician;
    }

    public void setTechnician(Boolean technician) {
        this.technician = technician;
    }

    public String getHis() {
        return his;
    }

    public void setHis(String his) {
        this.his = his;
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    public RankDTO getRank() {
        return rank;
    }

    public void setRank(RankDTO rank) {
        this.rank = rank;
    }

    public EmpServiceDTO getEmpService() {
        return empService;
    }

    public void setEmpService(EmpServiceDTO empService) {
        this.empService = empService;
    }

    public UnitDTO getUnit() {
        return unit;
    }

    public void setUnit(UnitDTO unit) {
        this.unit = unit;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeDTO)) {
            return false;
        }

        EmployeeDTO employeeDTO = (EmployeeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, employeeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", technician='" + getTechnician() + "'" +
            ", his='" + getHis() + "'" +
            ", serviceNo='" + getServiceNo() + "'" +
            ", rank=" + getRank() +
            ", empService=" + getEmpService() +
            ", unit=" + getUnit() +
            ", user=" + getUser() +
            "}";
    }
}
