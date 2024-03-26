package com.radiology.health.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.envers.Audited;

/**
 * A EmpService.
 */
@Entity
@Table(name = "emp_service")
@Audited
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmpService extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    private String login;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empService")
    @JsonIgnoreProperties(value = { "empService", "user", "employees" }, allowSetters = true)
    private Set<Rank> ranks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empService")
    @JsonIgnoreProperties(value = { "empService", "user", "employees" }, allowSetters = true)
    private Set<Unit> units = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empService")
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
    private Set<Employee> employees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EmpService id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public EmpService name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EmpService user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Rank> getRanks() {
        return this.ranks;
    }

    public void setRanks(Set<Rank> ranks) {
        if (this.ranks != null) {
            this.ranks.forEach(i -> i.setEmpService(null));
        }
        if (ranks != null) {
            ranks.forEach(i -> i.setEmpService(this));
        }
        this.ranks = ranks;
    }

    public EmpService ranks(Set<Rank> ranks) {
        this.setRanks(ranks);
        return this;
    }

    public EmpService addRank(Rank rank) {
        this.ranks.add(rank);
        rank.setEmpService(this);
        return this;
    }

    public EmpService removeRank(Rank rank) {
        this.ranks.remove(rank);
        rank.setEmpService(null);
        return this;
    }

    public Set<Unit> getUnits() {
        return this.units;
    }

    public void setUnits(Set<Unit> units) {
        if (this.units != null) {
            this.units.forEach(i -> i.setEmpService(null));
        }
        if (units != null) {
            units.forEach(i -> i.setEmpService(this));
        }
        this.units = units;
    }

    public EmpService units(Set<Unit> units) {
        this.setUnits(units);
        return this;
    }

    public EmpService addUnit(Unit unit) {
        this.units.add(unit);
        unit.setEmpService(this);
        return this;
    }

    public EmpService removeUnit(Unit unit) {
        this.units.remove(unit);
        unit.setEmpService(null);
        return this;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        if (this.employees != null) {
            this.employees.forEach(i -> i.setEmpService(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setEmpService(this));
        }
        this.employees = employees;
    }

    public EmpService employees(Set<Employee> employees) {
        this.setEmployees(employees);
        return this;
    }

    public EmpService addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setEmpService(this);
        return this;
    }

    public EmpService removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.setEmpService(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmpService)) {
            return false;
        }
        return getId() != null && getId().equals(((EmpService) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpService{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
