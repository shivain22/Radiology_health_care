package com.radiology.health.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.radiology.health.app.domain.PatientTestInfo} entity. This class is used
 * in {@link com.radiology.health.app.web.rest.PatientTestInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /patient-test-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PatientTestInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter patientInfoId;

    private LongFilter testTimingsId;

    private Boolean distinct;

    public PatientTestInfoCriteria() {}

    public PatientTestInfoCriteria(PatientTestInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.patientInfoId = other.patientInfoId == null ? null : other.patientInfoId.copy();
        this.testTimingsId = other.testTimingsId == null ? null : other.testTimingsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PatientTestInfoCriteria copy() {
        return new PatientTestInfoCriteria(this);
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

    public LongFilter getPatientInfoId() {
        return patientInfoId;
    }

    public LongFilter patientInfoId() {
        if (patientInfoId == null) {
            patientInfoId = new LongFilter();
        }
        return patientInfoId;
    }

    public void setPatientInfoId(LongFilter patientInfoId) {
        this.patientInfoId = patientInfoId;
    }

    public LongFilter getTestTimingsId() {
        return testTimingsId;
    }

    public LongFilter testTimingsId() {
        if (testTimingsId == null) {
            testTimingsId = new LongFilter();
        }
        return testTimingsId;
    }

    public void setTestTimingsId(LongFilter testTimingsId) {
        this.testTimingsId = testTimingsId;
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
        final PatientTestInfoCriteria that = (PatientTestInfoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(patientInfoId, that.patientInfoId) &&
            Objects.equals(testTimingsId, that.testTimingsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patientInfoId, testTimingsId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientTestInfoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (patientInfoId != null ? "patientInfoId=" + patientInfoId + ", " : "") +
            (testTimingsId != null ? "testTimingsId=" + testTimingsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
