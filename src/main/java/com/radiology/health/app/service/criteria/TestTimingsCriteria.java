package com.radiology.health.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.radiology.health.app.domain.TestTimings} entity. This class is used
 * in {@link com.radiology.health.app.web.rest.TestTimingsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /test-timings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TestTimingsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter timings;

    private LongFilter testCatogoriesId;

    private LongFilter patientTestInfoId;

    private Boolean distinct;

    public TestTimingsCriteria() {}

    public TestTimingsCriteria(TestTimingsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.timings = other.timings == null ? null : other.timings.copy();
        this.testCatogoriesId = other.testCatogoriesId == null ? null : other.testCatogoriesId.copy();
        this.patientTestInfoId = other.patientTestInfoId == null ? null : other.patientTestInfoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TestTimingsCriteria copy() {
        return new TestTimingsCriteria(this);
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

    public StringFilter getTimings() {
        return timings;
    }

    public StringFilter timings() {
        if (timings == null) {
            timings = new StringFilter();
        }
        return timings;
    }

    public void setTimings(StringFilter timings) {
        this.timings = timings;
    }

    public LongFilter getTestCatogoriesId() {
        return testCatogoriesId;
    }

    public LongFilter testCatogoriesId() {
        if (testCatogoriesId == null) {
            testCatogoriesId = new LongFilter();
        }
        return testCatogoriesId;
    }

    public void setTestCatogoriesId(LongFilter testCatogoriesId) {
        this.testCatogoriesId = testCatogoriesId;
    }

    public LongFilter getPatientTestInfoId() {
        return patientTestInfoId;
    }

    public LongFilter patientTestInfoId() {
        if (patientTestInfoId == null) {
            patientTestInfoId = new LongFilter();
        }
        return patientTestInfoId;
    }

    public void setPatientTestInfoId(LongFilter patientTestInfoId) {
        this.patientTestInfoId = patientTestInfoId;
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
        final TestTimingsCriteria that = (TestTimingsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(timings, that.timings) &&
            Objects.equals(testCatogoriesId, that.testCatogoriesId) &&
            Objects.equals(patientTestInfoId, that.patientTestInfoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timings, testCatogoriesId, patientTestInfoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TestTimingsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (timings != null ? "timings=" + timings + ", " : "") +
            (testCatogoriesId != null ? "testCatogoriesId=" + testCatogoriesId + ", " : "") +
            (patientTestInfoId != null ? "patientTestInfoId=" + patientTestInfoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
