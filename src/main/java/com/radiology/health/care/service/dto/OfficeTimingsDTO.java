package com.radiology.health.care.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.radiology.health.care.domain.OfficeTimings} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OfficeTimingsDTO implements Serializable {

    private Long id;

    private LocalDate date;

    @NotNull
    private LocalTime shiftStart;

    @NotNull
    private LocalTime shiftEnd;

    private Boolean defaultTimings;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getShiftStart() {
        return shiftStart;
    }

    public void setShiftStart(LocalTime shiftStart) {
        this.shiftStart = shiftStart;
    }

    public LocalTime getShiftEnd() {
        return shiftEnd;
    }

    public void setShiftEnd(LocalTime shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public Boolean getDefaultTimings() {
        return defaultTimings;
    }

    public void setDefaultTimings(Boolean defaultTimings) {
        this.defaultTimings = defaultTimings;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfficeTimingsDTO)) {
            return false;
        }

        OfficeTimingsDTO officeTimingsDTO = (OfficeTimingsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, officeTimingsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OfficeTimingsDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", shiftStart='" + getShiftStart() + "'" +
            ", shiftEnd='" + getShiftEnd() + "'" +
            ", defaultTimings='" + getDefaultTimings() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
