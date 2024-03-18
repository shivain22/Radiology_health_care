package com.radiology.health.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.radiology.health.app.domain.PatientInfo} entity. This class is used
 * in {@link com.radiology.health.app.web.rest.PatientInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /patient-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PatientInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter age;

    private StringFilter gender;

    private StringFilter relation;

    private LongFilter employeeId;

    private LongFilter patientTestInfoId;

    private Boolean distinct;

    public PatientInfoCriteria() {}

    public PatientInfoCriteria(PatientInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.age = other.age == null ? null : other.age.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.relation = other.relation == null ? null : other.relation.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.patientTestInfoId = other.patientTestInfoId == null ? null : other.patientTestInfoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PatientInfoCriteria copy() {
        return new PatientInfoCriteria(this);
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

    public IntegerFilter getAge() {
        return age;
    }

    public IntegerFilter age() {
        if (age == null) {
            age = new IntegerFilter();
        }
        return age;
    }

    public void setAge(IntegerFilter age) {
        this.age = age;
    }

    public StringFilter getGender() {
        return gender;
    }

    public StringFilter gender() {
        if (gender == null) {
            gender = new StringFilter();
        }
        return gender;
    }

    public void setGender(StringFilter gender) {
        this.gender = gender;
    }

    public StringFilter getRelation() {
        return relation;
    }

    public StringFilter relation() {
        if (relation == null) {
            relation = new StringFilter();
        }
        return relation;
    }

    public void setRelation(StringFilter relation) {
        this.relation = relation;
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
        final PatientInfoCriteria that = (PatientInfoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(age, that.age) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(relation, that.relation) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(patientTestInfoId, that.patientTestInfoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, age, gender, relation, employeeId, patientTestInfoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientInfoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (age != null ? "age=" + age + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (relation != null ? "relation=" + relation + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (patientTestInfoId != null ? "patientTestInfoId=" + patientTestInfoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
