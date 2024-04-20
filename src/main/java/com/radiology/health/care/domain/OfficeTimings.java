package com.radiology.health.care.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

/**
 * A OfficeTimings.
 */
@Entity
@Table(name = "office_timings")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OfficeTimings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @NotNull
    @Column(name = "shift_start", nullable = false)
    private LocalTime shiftStart;

    @NotNull
    @Column(name = "shift_end", nullable = false)
    private LocalTime shiftEnd;

    @Column(name = "default_timings")
    private Boolean defaultTimings;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OfficeTimings id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public OfficeTimings date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getShiftStart() {
        return this.shiftStart;
    }

    public OfficeTimings shiftStart(LocalTime shiftStart) {
        this.setShiftStart(shiftStart);
        return this;
    }

    public void setShiftStart(LocalTime shiftStart) {
        this.shiftStart = shiftStart;
    }

    public LocalTime getShiftEnd() {
        return this.shiftEnd;
    }

    public OfficeTimings shiftEnd(LocalTime shiftEnd) {
        this.setShiftEnd(shiftEnd);
        return this;
    }

    public void setShiftEnd(LocalTime shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public Boolean getDefaultTimings() {
        return this.defaultTimings;
    }

    public OfficeTimings defaultTimings(Boolean defaultTimings) {
        this.setDefaultTimings(defaultTimings);
        return this;
    }

    public void setDefaultTimings(Boolean defaultTimings) {
        this.defaultTimings = defaultTimings;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OfficeTimings user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfficeTimings)) {
            return false;
        }
        return getId() != null && getId().equals(((OfficeTimings) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OfficeTimings{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", shiftStart='" + getShiftStart() + "'" +
            ", shiftEnd='" + getShiftEnd() + "'" +
            ", defaultTimings='" + getDefaultTimings() + "'" +
            "}";
    }
}
