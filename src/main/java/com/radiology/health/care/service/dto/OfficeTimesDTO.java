package com.radiology.health.care.service.dto;

import com.radiology.health.care.domain.ShiftTimings;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

public class OfficeTimesDTO {

    private Long id;

    @NotNull
    private LocalDate date;

    @NotNull
    private List<ShiftTimings> shiftTimes;

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

    public List<ShiftTimings> getShiftTimes() {
        return shiftTimes;
    }

    public void setShiftTimes(List<ShiftTimings> shiftTimes) {
        this.shiftTimes = shiftTimes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfficeTimesDTO)) {
            return false;
        }

        OfficeTimesDTO officeTimesDTO = (OfficeTimesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, officeTimesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OfficeTimesDTO{" +
            "id=" + getId() +
            ", date=" + getDate() +
            ", shiftTimes=" + getShiftTimes() +
            '}';
    }
}
