package com.radiology.health.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.envers.Audited;

/**
 * A Rank.
 */
@Entity
@Table(name = "jhi_rank")
@Audited
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Rank extends AbstractAuditingEntity<Long> implements Serializable {

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
    @JsonIgnoreProperties(value = { "user", "ranks", "units", "employees" }, allowSetters = true)
    private EmpService empService;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rank")
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

    public Rank id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Rank name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EmpService getEmpService() {
        return this.empService;
    }

    public void setEmpService(EmpService empService) {
        this.empService = empService;
    }

    public Rank empService(EmpService empService) {
        this.setEmpService(empService);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Rank user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        if (this.employees != null) {
            this.employees.forEach(i -> i.setRank(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setRank(this));
        }
        this.employees = employees;
    }

    public Rank employees(Set<Employee> employees) {
        this.setEmployees(employees);
        return this;
    }

    public Rank addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setRank(this);
        return this;
    }

    public Rank removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.setRank(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rank)) {
            return false;
        }
        return getId() != null && getId().equals(((Rank) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rank{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
