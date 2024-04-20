package com.radiology.health.care.service.dto;

import com.radiology.health.care.domain.ShiftTimings;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

public class DefaultOfficeTimingsDTO {

    private Long id;

    @NotNull
    private List<ShiftTimings> shiftTimes;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof DefaultOfficeTimingsDTO)) {
            return false;
        }

        DefaultOfficeTimingsDTO defaultOfficeTimingsDTO = (DefaultOfficeTimingsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, defaultOfficeTimingsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DefaultOfficeTimingsDTO{" +
            "id=" + getId() +
            ", shiftTimes=" + getShiftTimes() +
            ", userId=" + getUserId() +
            '}';
    }
}
