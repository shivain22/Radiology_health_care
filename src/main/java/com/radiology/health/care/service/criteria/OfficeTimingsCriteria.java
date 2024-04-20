package com.radiology.health.care.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.radiology.health.care.domain.OfficeTimings} entity. This class is used
 * in {@link com.radiology.health.care.web.rest.OfficeTimingsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /office-timings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OfficeTimingsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter date;

    private ZonedDateTimeFilter shiftStart;

    private ZonedDateTimeFilter shiftEnd;

    private BooleanFilter defaultTimings;

    private LongFilter userId;

    private Boolean distinct;

    public OfficeTimingsCriteria() {}

    public OfficeTimingsCriteria(OfficeTimingsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.shiftStart = other.shiftStart == null ? null : other.shiftStart.copy();
        this.shiftEnd = other.shiftEnd == null ? null : other.shiftEnd.copy();
        this.defaultTimings = other.defaultTimings == null ? null : other.defaultTimings.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public OfficeTimingsCriteria copy() {
        return new OfficeTimingsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public LocalDateFilter date() {
        if (date == null) {
            date = new LocalDateFilter();
        }
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public ZonedDateTimeFilter getShiftStart() {
        return shiftStart;
    }

    public ZonedDateTimeFilter shiftStart() {
        if (shiftStart == null) {
            shiftStart = new ZonedDateTimeFilter();
        }
        return shiftStart;
    }

    public void setShiftStart(ZonedDateTimeFilter shiftStart) {
        this.shiftStart = shiftStart;
    }

    public ZonedDateTimeFilter getShiftEnd() {
        return shiftEnd;
    }

    public ZonedDateTimeFilter shiftEnd() {
        if (shiftEnd == null) {
            shiftEnd = new ZonedDateTimeFilter();
        }
        return shiftEnd;
    }

    public void setShiftEnd(ZonedDateTimeFilter shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public BooleanFilter getDefaultTimings() {
        return defaultTimings;
    }

    public BooleanFilter defaultTimings() {
        if (defaultTimings == null) {
            defaultTimings = new BooleanFilter();
        }
        return defaultTimings;
    }

    public void setDefaultTimings(BooleanFilter defaultTimings) {
        this.defaultTimings = defaultTimings;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public LongFilter userId() {
        if (userId == null) {
            userId = new LongFilter();
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OfficeTimingsCriteria that = (OfficeTimingsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(shiftStart, that.shiftStart) &&
            Objects.equals(shiftEnd, that.shiftEnd) &&
            Objects.equals(defaultTimings, that.defaultTimings) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, shiftStart, shiftEnd, defaultTimings, userId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OfficeTimingsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (shiftStart != null ? "shiftStart=" + shiftStart + ", " : "") +
            (shiftEnd != null ? "shiftEnd=" + shiftEnd + ", " : "") +
            (defaultTimings != null ? "defaultTimings=" + defaultTimings + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
