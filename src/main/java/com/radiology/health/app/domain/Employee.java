package com.radiology.health.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "his")
    private String his;

    @Column(name = "service_no")
    private String serviceNo;

    @Column(name = "name")
    private String name;

    @Column(name = "technician")
    private Integer technician;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "employees" }, allowSetters = true)
    private Unit unit;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "employees", "ranks" }, allowSetters = true)
    private Services services;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "services", "employees" }, allowSetters = true)
    private Rank rank;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee", "patientTestInfos" }, allowSetters = true)
    private Set<PatientInfo> patientInfos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee", "equipments" }, allowSetters = true)
    private Set<TechicianEquipmentMapping> techicianEquipmentMappings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHis() {
        return this.his;
    }

    public Employee his(String his) {
        this.setHis(his);
        return this;
    }

    public void setHis(String his) {
        this.his = his;
    }

    public String getServiceNo() {
        return this.serviceNo;
    }

    public Employee serviceNo(String serviceNo) {
        this.setServiceNo(serviceNo);
        return this;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    public String getName() {
        return this.name;
    }

    public Employee name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTechnician() {
        return this.technician;
    }

    public Employee technician(Integer technician) {
        this.setTechnician(technician);
        return this;
    }

    public void setTechnician(Integer technician) {
        this.technician = technician;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Employee unit(Unit unit) {
        this.setUnit(unit);
        return this;
    }

    public Services getServices() {
        return this.services;
    }

    public void setServices(Services services) {
        this.services = services;
    }

    public Employee services(Services services) {
        this.setServices(services);
        return this;
    }

    public Rank getRank() {
        return this.rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Employee rank(Rank rank) {
        this.setRank(rank);
        return this;
    }

    public Set<PatientInfo> getPatientInfos() {
        return this.patientInfos;
    }

    public void setPatientInfos(Set<PatientInfo> patientInfos) {
        if (this.patientInfos != null) {
            this.patientInfos.forEach(i -> i.setEmployee(null));
        }
        if (patientInfos != null) {
            patientInfos.forEach(i -> i.setEmployee(this));
        }
        this.patientInfos = patientInfos;
    }

    public Employee patientInfos(Set<PatientInfo> patientInfos) {
        this.setPatientInfos(patientInfos);
        return this;
    }

    public Employee addPatientInfo(PatientInfo patientInfo) {
        this.patientInfos.add(patientInfo);
        patientInfo.setEmployee(this);
        return this;
    }

    public Employee removePatientInfo(PatientInfo patientInfo) {
        this.patientInfos.remove(patientInfo);
        patientInfo.setEmployee(null);
        return this;
    }

    public Set<TechicianEquipmentMapping> getTechicianEquipmentMappings() {
        return this.techicianEquipmentMappings;
    }

    public void setTechicianEquipmentMappings(Set<TechicianEquipmentMapping> techicianEquipmentMappings) {
        if (this.techicianEquipmentMappings != null) {
            this.techicianEquipmentMappings.forEach(i -> i.setEmployee(null));
        }
        if (techicianEquipmentMappings != null) {
            techicianEquipmentMappings.forEach(i -> i.setEmployee(this));
        }
        this.techicianEquipmentMappings = techicianEquipmentMappings;
    }

    public Employee techicianEquipmentMappings(Set<TechicianEquipmentMapping> techicianEquipmentMappings) {
        this.setTechicianEquipmentMappings(techicianEquipmentMappings);
        return this;
    }

    public Employee addTechicianEquipmentMapping(TechicianEquipmentMapping techicianEquipmentMapping) {
        this.techicianEquipmentMappings.add(techicianEquipmentMapping);
        techicianEquipmentMapping.setEmployee(this);
        return this;
    }

    public Employee removeTechicianEquipmentMapping(TechicianEquipmentMapping techicianEquipmentMapping) {
        this.techicianEquipmentMappings.remove(techicianEquipmentMapping);
        techicianEquipmentMapping.setEmployee(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return getId() != null && getId().equals(((Employee) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", his='" + getHis() + "'" +
            ", serviceNo='" + getServiceNo() + "'" +
            ", name='" + getName() + "'" +
            ", technician=" + getTechnician() +
            "}";
    }
}
