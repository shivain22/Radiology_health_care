package com.radiology.health.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @Column(name = "age")
    private Integer age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "relation")
    private String relation;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "unit", "services", "rank", "patientInfos", "techicianEquipmentMappings" }, allowSetters = true)
    private Employee employee;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patientInfo")
    @JsonIgnoreProperties(value = { "patientInfo", "testTimings" }, allowSetters = true)
    private Set<PatientTestInfo> patientTestInfos = new HashSet<>();

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

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public PatientInfo employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    public Set<PatientTestInfo> getPatientTestInfos() {
        return this.patientTestInfos;
    }

    public void setPatientTestInfos(Set<PatientTestInfo> patientTestInfos) {
        if (this.patientTestInfos != null) {
            this.patientTestInfos.forEach(i -> i.setPatientInfo(null));
        }
        if (patientTestInfos != null) {
            patientTestInfos.forEach(i -> i.setPatientInfo(this));
        }
        this.patientTestInfos = patientTestInfos;
    }

    public PatientInfo patientTestInfos(Set<PatientTestInfo> patientTestInfos) {
        this.setPatientTestInfos(patientTestInfos);
        return this;
    }

    public PatientInfo addPatientTestInfo(PatientTestInfo patientTestInfo) {
        this.patientTestInfos.add(patientTestInfo);
        patientTestInfo.setPatientInfo(this);
        return this;
    }

    public PatientInfo removePatientTestInfo(PatientTestInfo patientTestInfo) {
        this.patientTestInfos.remove(patientTestInfo);
        patientTestInfo.setPatientInfo(null);
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
            ", age=" + getAge() +
            ", gender='" + getGender() + "'" +
            ", relation='" + getRelation() + "'" +
            "}";
    }
}
