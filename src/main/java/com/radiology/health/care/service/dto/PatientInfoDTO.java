package com.radiology.health.care.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.radiology.health.care.domain.PatientInfo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PatientInfoDTO implements Serializable {

    private Long id;

    private String name;

    private Integer age;

    private String gender;

    private String dateOfBirth;

    private Long mobile;

    private String relation;

    private EmployeeDTO employeeId;

    private EmployeeDTO employeeHis;

    private EmployeeDTO employeeServiceNo;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public EmployeeDTO getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(EmployeeDTO employeeId) {
        this.employeeId = employeeId;
    }

    public EmployeeDTO getEmployeeHis() {
        return employeeHis;
    }

    public void setEmployeeHis(EmployeeDTO employeeHis) {
        this.employeeHis = employeeHis;
    }

    public EmployeeDTO getEmployeeServiceNo() {
        return employeeServiceNo;
    }

    public void setEmployeeServiceNo(EmployeeDTO employeeServiceNo) {
        this.employeeServiceNo = employeeServiceNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientInfoDTO)) {
            return false;
        }

        PatientInfoDTO patientInfoDTO = (PatientInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, patientInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientInfoDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", age=" + getAge() +
            ", gender='" + getGender() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", mobile=" + getMobile() +
            ", relation='" + getRelation() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", employeeHis=" + getEmployeeHis() +
            ", employeeServiceNo=" + getEmployeeServiceNo() +
            "}";
    }
}
