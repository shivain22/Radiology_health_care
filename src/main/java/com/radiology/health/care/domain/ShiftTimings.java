package com.radiology.health.care.domain;

import java.time.LocalTime;
import java.time.ZonedDateTime;

public class ShiftTimings {

    private LocalTime startTime;

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    private LocalTime endTime;
}
