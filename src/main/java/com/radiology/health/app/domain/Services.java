package com.radiology.health.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Services.
 */
@Entity
@Table(name = "services")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Services implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "services")
    @JsonIgnoreProperties(value = { "unit", "services", "rank", "patientInfos", "techicianEquipmentMappings" }, allowSetters = true)
    private Set<Employee> employees = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "services")
    @JsonIgnoreProperties(value = { "services", "employees" }, allowSetters = true)
    private Set<Rank> ranks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Services id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Services name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        if (this.employees != null) {
            this.employees.forEach(i -> i.setServices(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setServices(this));
        }
        this.employees = employees;
    }

    public Services employees(Set<Employee> employees) {
        this.setEmployees(employees);
        return this;
    }

    public Services addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setServices(this);
        return this;
    }

    public Services removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.setServices(null);
        return this;
    }

    public Set<Rank> getRanks() {
        return this.ranks;
    }

    public void setRanks(Set<Rank> ranks) {
        if (this.ranks != null) {
            this.ranks.forEach(i -> i.setServices(null));
        }
        if (ranks != null) {
            ranks.forEach(i -> i.setServices(this));
        }
        this.ranks = ranks;
    }

    public Services ranks(Set<Rank> ranks) {
        this.setRanks(ranks);
        return this;
    }

    public Services addRank(Rank rank) {
        this.ranks.add(rank);
        rank.setServices(this);
        return this;
    }

    public Services removeRank(Rank rank) {
        this.ranks.remove(rank);
        rank.setServices(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Services)) {
            return false;
        }
        return getId() != null && getId().equals(((Services) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Services{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
