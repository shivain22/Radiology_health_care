package com.radiology.health.care.service.criteria;

import com.radiology.health.care.domain.enumeration.rankDivisions;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.radiology.health.care.domain.Rank} entity. This class is used
 * in {@link com.radiology.health.care.web.rest.RankResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ranks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RankCriteria implements Serializable, Criteria {

    /**
     * Class for filtering rankDivisions
     */
    public static class rankDivisionsFilter extends Filter<rankDivisions> {

        public rankDivisionsFilter() {}

        public rankDivisionsFilter(rankDivisionsFilter filter) {
            super(filter);
        }

        @Override
        public rankDivisionsFilter copy() {
            return new rankDivisionsFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter shortName;

    private rankDivisionsFilter division;

    private LongFilter empServiceId;

    private LongFilter userId;

    private LongFilter employeeId;

    private Boolean distinct;

    public RankCriteria() {}

    public RankCriteria(RankCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.shortName = other.shortName == null ? null : other.shortName.copy();
        this.division = other.division == null ? null : other.division.copy();
        this.empServiceId = other.empServiceId == null ? null : other.empServiceId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RankCriteria copy() {
        return new RankCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getShortName() {
        return shortName;
    }

    public StringFilter shortName() {
        if (shortName == null) {
            shortName = new StringFilter();
        }
        return shortName;
    }

    public void setShortName(StringFilter shortName) {
        this.shortName = shortName;
    }

    public rankDivisionsFilter getDivision() {
        return division;
    }

    public rankDivisionsFilter division() {
        if (division == null) {
            division = new rankDivisionsFilter();
        }
        return division;
    }

    public void setDivision(rankDivisionsFilter division) {
        this.division = division;
    }

    public LongFilter getEmpServiceId() {
        return empServiceId;
    }

    public LongFilter empServiceId() {
        if (empServiceId == null) {
            empServiceId = new LongFilter();
        }
        return empServiceId;
    }

    public void setEmpServiceId(LongFilter empServiceId) {
        this.empServiceId = empServiceId;
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

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
            employeeId = new LongFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
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
        final RankCriteria that = (RankCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(shortName, that.shortName) &&
            Objects.equals(division, that.division) &&
            Objects.equals(empServiceId, that.empServiceId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shortName, division, empServiceId, userId, employeeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RankCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (shortName != null ? "shortName=" + shortName + ", " : "") +
            (division != null ? "division=" + division + ", " : "") +
            (empServiceId != null ? "empServiceId=" + empServiceId + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
