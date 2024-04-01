package com.radiology.health.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A PatientInfo.
 */
@Entity
@Table(name = "patient_info")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PatientInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "relation")
    private String relation;

    @Column(name = "mobile")
    private Long mobile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "rank",
            "empService",
            "unit",
            "user",
            "technicianEquipmentMappings",
            "patientInfoEmployeeIds",
            "patientInfoEmployeeHis",
            "patientInfoEmployeeServiceNos",
        },
        allowSetters = true
    )
    private Employee employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "rank",
            "empService",
            "unit",
            "user",
            "technicianEquipmentMappings",
            "patientInfoEmployeeIds",
            "patientInfoEmployeeHis",
            "patientInfoEmployeeServiceNos",
        },
        allowSetters = true
    )
    private Employee employeeHis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "rank",
            "empService",
            "unit",
            "user",
            "technicianEquipmentMappings",
            "patientInfoEmployeeIds",
            "patientInfoEmployeeHis",
            "patientInfoEmployeeServiceNos",
        },
        allowSetters = true
    )
    private Employee employeeServiceNo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patientInfo")
    @JsonIgnoreProperties(value = { "patientInfo", "testCategories" }, allowSetters = true)
    private Set<PatientTestTimings> patientTestTimings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PatientInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public PatientInfo name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return this.age;
    }

    public PatientInfo age(Integer age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return this.gender;
    }

    public PatientInfo gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public PatientInfo dateOfBirth(String dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRelation() {
        return this.relation;
    }

    public PatientInfo relation(String relation) {
        this.setRelation(relation);
        return this;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Long getMobile() {
        return this.mobile;
    }

    public PatientInfo mobile(Long mobile) {
        this.setMobile(mobile);
        return this;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public Employee getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(Employee employee) {
        this.employeeId = employee;
    }

    public PatientInfo employeeId(Employee employee) {
        this.setEmployeeId(employee);
        return this;
    }

    public Employee getEmployeeHis() {
        return this.employeeHis;
    }

    public void setEmployeeHis(Employee employee) {
        this.employeeHis = employee;
    }

    public PatientInfo employeeHis(Employee employee) {
        this.setEmployeeHis(employee);
        return this;
    }

    public Employee getEmployeeServiceNo() {
        return this.employeeServiceNo;
    }

    public void setEmployeeServiceNo(Employee employee) {
        this.employeeServiceNo = employee;
    }

    public PatientInfo employeeServiceNo(Employee employee) {
        this.setEmployeeServiceNo(employee);
        return this;
    }

    public Set<PatientTestTimings> getPatientTestTimings() {
        return this.patientTestTimings;
    }

    public void setPatientTestTimings(Set<PatientTestTimings> patientTestTimings) {
        if (this.patientTestTimings != null) {
            this.patientTestTimings.forEach(i -> i.setPatientInfo(null));
        }
        if (patientTestTimings != null) {
            patientTestTimings.forEach(i -> i.setPatientInfo(this));
        }
        this.patientTestTimings = patientTestTimings;
    }

    public PatientInfo patientTestTimings(Set<PatientTestTimings> patientTestTimings) {
        this.setPatientTestTimings(patientTestTimings);
        return this;
    }

    public PatientInfo addPatientTestTimings(PatientTestTimings patientTestTimings) {
        this.patientTestTimings.add(patientTestTimings);
        patientTestTimings.setPatientInfo(this);
        return this;
    }

    public PatientInfo removePatientTestTimings(PatientTestTimings patientTestTimings) {
        this.patientTestTimings.remove(patientTestTimings);
        patientTestTimings.setPatientInfo(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientInfo)) {
            return false;
        }
        return getId() != null && getId().equals(((PatientInfo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientInfo{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", age=" + getAge() +
            ", gender='" + getGender() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", relation='" + getRelation() + "'" +
            ", mobile=" + getMobile() +
            "}";
    }
}
