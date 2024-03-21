package com.radiology.health.care.domain;

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
public class Employee extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "technician")
    private Boolean technician;

    @Column(name = "his", unique = true)
    private String his;

    @Column(name = "service_no", unique = true)
    private String serviceNo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "empService", "user", "employees" }, allowSetters = true)
    private Rank rank;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "user", "ranks", "units", "employees" }, allowSetters = true)
    private EmpService empService;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "empService", "user", "employees" }, allowSetters = true)
    private Unit unit;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    @JsonIgnoreProperties(value = { "equipment", "employee", "user" }, allowSetters = true)
    private Set<TechnicianEquipmentMapping> technicianEquipmentMappings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeId")
    @JsonIgnoreProperties(value = { "employeeId", "employeeHis", "employeeServiceNo", "patientTestTimings" }, allowSetters = true)
    private Set<PatientInfo> patientInfoEmployeeIds = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeHis")
    @JsonIgnoreProperties(value = { "employeeId", "employeeHis", "employeeServiceNo", "patientTestTimings" }, allowSetters = true)
    private Set<PatientInfo> patientInfoEmployeeHis = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeServiceNo")
    @JsonIgnoreProperties(value = { "employeeId", "employeeHis", "employeeServiceNo", "patientTestTimings" }, allowSetters = true)
    private Set<PatientInfo> patientInfoEmployeeServiceNos = new HashSet<>();

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

    public Boolean getTechnician() {
        return this.technician;
    }

    public Employee technician(Boolean technician) {
        this.setTechnician(technician);
        return this;
    }

    public void setTechnician(Boolean technician) {
        this.technician = technician;
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

    public EmpService getEmpService() {
        return this.empService;
    }

    public void setEmpService(EmpService empService) {
        this.empService = empService;
    }

    public Employee empService(EmpService empService) {
        this.setEmpService(empService);
        return this;
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

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Employee user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<TechnicianEquipmentMapping> getTechnicianEquipmentMappings() {
        return this.technicianEquipmentMappings;
    }

    public void setTechnicianEquipmentMappings(Set<TechnicianEquipmentMapping> technicianEquipmentMappings) {
        if (this.technicianEquipmentMappings != null) {
            this.technicianEquipmentMappings.forEach(i -> i.setEmployee(null));
        }
        if (technicianEquipmentMappings != null) {
            technicianEquipmentMappings.forEach(i -> i.setEmployee(this));
        }
        this.technicianEquipmentMappings = technicianEquipmentMappings;
    }

    public Employee technicianEquipmentMappings(Set<TechnicianEquipmentMapping> technicianEquipmentMappings) {
        this.setTechnicianEquipmentMappings(technicianEquipmentMappings);
        return this;
    }

    public Employee addTechnicianEquipmentMapping(TechnicianEquipmentMapping technicianEquipmentMapping) {
        this.technicianEquipmentMappings.add(technicianEquipmentMapping);
        technicianEquipmentMapping.setEmployee(this);
        return this;
    }

    public Employee removeTechnicianEquipmentMapping(TechnicianEquipmentMapping technicianEquipmentMapping) {
        this.technicianEquipmentMappings.remove(technicianEquipmentMapping);
        technicianEquipmentMapping.setEmployee(null);
        return this;
    }

    public Set<PatientInfo> getPatientInfoEmployeeIds() {
        return this.patientInfoEmployeeIds;
    }

    public void setPatientInfoEmployeeIds(Set<PatientInfo> patientInfos) {
        if (this.patientInfoEmployeeIds != null) {
            this.patientInfoEmployeeIds.forEach(i -> i.setEmployeeId(null));
        }
        if (patientInfos != null) {
            patientInfos.forEach(i -> i.setEmployeeId(this));
        }
        this.patientInfoEmployeeIds = patientInfos;
    }

    public Employee patientInfoEmployeeIds(Set<PatientInfo> patientInfos) {
        this.setPatientInfoEmployeeIds(patientInfos);
        return this;
    }

    public Employee addPatientInfoEmployeeId(PatientInfo patientInfo) {
        this.patientInfoEmployeeIds.add(patientInfo);
        patientInfo.setEmployeeId(this);
        return this;
    }

    public Employee removePatientInfoEmployeeId(PatientInfo patientInfo) {
        this.patientInfoEmployeeIds.remove(patientInfo);
        patientInfo.setEmployeeId(null);
        return this;
    }

    public Set<PatientInfo> getPatientInfoEmployeeHis() {
        return this.patientInfoEmployeeHis;
    }

    public void setPatientInfoEmployeeHis(Set<PatientInfo> patientInfos) {
        if (this.patientInfoEmployeeHis != null) {
            this.patientInfoEmployeeHis.forEach(i -> i.setEmployeeHis(null));
        }
        if (patientInfos != null) {
            patientInfos.forEach(i -> i.setEmployeeHis(this));
        }
        this.patientInfoEmployeeHis = patientInfos;
    }

    public Employee patientInfoEmployeeHis(Set<PatientInfo> patientInfos) {
        this.setPatientInfoEmployeeHis(patientInfos);
        return this;
    }

    public Employee addPatientInfoEmployeeHis(PatientInfo patientInfo) {
        this.patientInfoEmployeeHis.add(patientInfo);
        patientInfo.setEmployeeHis(this);
        return this;
    }

    public Employee removePatientInfoEmployeeHis(PatientInfo patientInfo) {
        this.patientInfoEmployeeHis.remove(patientInfo);
        patientInfo.setEmployeeHis(null);
        return this;
    }

    public Set<PatientInfo> getPatientInfoEmployeeServiceNos() {
        return this.patientInfoEmployeeServiceNos;
    }

    public void setPatientInfoEmployeeServiceNos(Set<PatientInfo> patientInfos) {
        if (this.patientInfoEmployeeServiceNos != null) {
            this.patientInfoEmployeeServiceNos.forEach(i -> i.setEmployeeServiceNo(null));
        }
        if (patientInfos != null) {
            patientInfos.forEach(i -> i.setEmployeeServiceNo(this));
        }
        this.patientInfoEmployeeServiceNos = patientInfos;
    }

    public Employee patientInfoEmployeeServiceNos(Set<PatientInfo> patientInfos) {
        this.setPatientInfoEmployeeServiceNos(patientInfos);
        return this;
    }

    public Employee addPatientInfoEmployeeServiceNo(PatientInfo patientInfo) {
        this.patientInfoEmployeeServiceNos.add(patientInfo);
        patientInfo.setEmployeeServiceNo(this);
        return this;
    }

    public Employee removePatientInfoEmployeeServiceNo(PatientInfo patientInfo) {
        this.patientInfoEmployeeServiceNos.remove(patientInfo);
        patientInfo.setEmployeeServiceNo(null);
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
            ", name='" + getName() + "'" +
            ", technician='" + getTechnician() + "'" +
            ", his='" + getHis() + "'" +
            ", serviceNo='" + getServiceNo() + "'" +
            "}";
    }
}
