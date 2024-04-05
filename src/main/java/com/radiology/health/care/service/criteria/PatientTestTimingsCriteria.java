package com.radiology.health.care.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.radiology.health.care.domain.PatientTestTimings} entity. This class is used
 * in {@link com.radiology.health.care.web.rest.PatientTestTimingsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /patient-test-timings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PatientTestTimingsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter priority;

    private StringFilter clinicalNote;

    private StringFilter spclInstruction;

    private StringFilter status;

    private ZonedDateTimeFilter startTiming;

    private ZonedDateTimeFilter endTime;

    private LongFilter patientInfoId;

    private LongFilter testCategoriesId;

    private Boolean distinct;

    public PatientTestTimingsCriteria() {}

    public PatientTestTimingsCriteria(PatientTestTimingsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.clinicalNote = other.clinicalNote == null ? null : other.clinicalNote.copy();
        this.spclInstruction = other.spclInstruction == null ? null : other.spclInstruction.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.startTiming = other.startTiming == null ? null : other.startTiming.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.patientInfoId = other.patientInfoId == null ? null : other.patientInfoId.copy();
        this.testCategoriesId = other.testCategoriesId == null ? null : other.testCategoriesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PatientTestTimingsCriteria copy() {
        return new PatientTestTimingsCriteria(this);
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

    public StringFilter getPriority() {
        return priority;
    }

    public StringFilter priority() {
        if (priority == null) {
            priority = new StringFilter();
        }
        return priority;
    }

    public void setPriority(StringFilter priority) {
        this.priority = priority;
    }

    public StringFilter getClinicalNote() {
        return clinicalNote;
    }

    public StringFilter clinicalNote() {
        if (clinicalNote == null) {
            clinicalNote = new StringFilter();
        }
        return clinicalNote;
    }

    public void setClinicalNote(StringFilter clinicalNote) {
        this.clinicalNote = clinicalNote;
    }

    public StringFilter getSpclInstruction() {
        return spclInstruction;
    }

    public StringFilter spclInstruction() {
        if (spclInstruction == null) {
            spclInstruction = new StringFilter();
        }
        return spclInstruction;
    }

    public void setSpclInstruction(StringFilter spclInstruction) {
        this.spclInstruction = spclInstruction;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public ZonedDateTimeFilter getStartTiming() {
        return startTiming;
    }

    public ZonedDateTimeFilter startTiming() {
        if (startTiming == null) {
            startTiming = new ZonedDateTimeFilter();
        }
        return startTiming;
    }

    public void setStartTiming(ZonedDateTimeFilter startTiming) {
        this.startTiming = startTiming;
    }

    public ZonedDateTimeFilter getEndTime() {
        return endTime;
    }

    public ZonedDateTimeFilter endTime() {
        if (endTime == null) {
            endTime = new ZonedDateTimeFilter();
        }
        return endTime;
    }

    public void setEndTime(ZonedDateTimeFilter endTime) {
        this.endTime = endTime;
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

    public LongFilter getTestCategoriesId() {
        return testCategoriesId;
    }

    public LongFilter testCategoriesId() {
        if (testCategoriesId == null) {
            testCategoriesId = new LongFilter();
        }
        return testCategoriesId;
    }

    public void setTestCategoriesId(LongFilter testCategoriesId) {
        this.testCategoriesId = testCategoriesId;
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
        final PatientTestTimingsCriteria that = (PatientTestTimingsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(clinicalNote, that.clinicalNote) &&
            Objects.equals(spclInstruction, that.spclInstruction) &&
            Objects.equals(status, that.status) &&
            Objects.equals(startTiming, that.startTiming) &&
            Objects.equals(endTime, that.endTime) &&
            Objects.equals(patientInfoId, that.patientInfoId) &&
            Objects.equals(testCategoriesId, that.testCategoriesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            priority,
            clinicalNote,
            spclInstruction,
            status,
            startTiming,
            endTime,
            patientInfoId,
            testCategoriesId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientTestTimingsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (priority != null ? "priority=" + priority + ", " : "") +
            (clinicalNote != null ? "clinicalNote=" + clinicalNote + ", " : "") +
            (spclInstruction != null ? "spclInstruction=" + spclInstruction + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (startTiming != null ? "startTiming=" + startTiming + ", " : "") +
            (endTime != null ? "endTime=" + endTime + ", " : "") +
            (patientInfoId != null ? "patientInfoId=" + patientInfoId + ", " : "") +
            (testCategoriesId != null ? "testCategoriesId=" + testCategoriesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
