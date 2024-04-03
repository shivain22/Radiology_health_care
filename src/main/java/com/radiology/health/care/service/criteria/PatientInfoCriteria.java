package com.radiology.health.care.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.radiology.health.care.domain.PatientInfo} entity. This class is used
 * in {@link com.radiology.health.care.web.rest.PatientInfoResource} to receive all the possible filtering options from
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

    private StringFilter name;

    private IntegerFilter age;

    private StringFilter gender;

    private StringFilter dateOfBirth;

    private LongFilter mobile;

    private StringFilter relation;

    private LongFilter employeeIdId;

    private LongFilter employeeHisId;

    private LongFilter employeeServiceNoId;

    private LongFilter patientTestTimingsId;

    private Boolean distinct;

    public PatientInfoCriteria() {}

    public PatientInfoCriteria(PatientInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.age = other.age == null ? null : other.age.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.dateOfBirth = other.dateOfBirth == null ? null : other.dateOfBirth.copy();
        this.mobile = other.mobile == null ? null : other.mobile.copy();
        this.relation = other.relation == null ? null : other.relation.copy();
        this.employeeIdId = other.employeeIdId == null ? null : other.employeeIdId.copy();
        this.employeeHisId = other.employeeHisId == null ? null : other.employeeHisId.copy();
        this.employeeServiceNoId = other.employeeServiceNoId == null ? null : other.employeeServiceNoId.copy();
        this.patientTestTimingsId = other.patientTestTimingsId == null ? null : other.patientTestTimingsId.copy();
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

    public StringFilter getDateOfBirth() {
        return dateOfBirth;
    }

    public StringFilter dateOfBirth() {
        if (dateOfBirth == null) {
            dateOfBirth = new StringFilter();
        }
        return dateOfBirth;
    }

    public void setDateOfBirth(StringFilter dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LongFilter getMobile() {
        return mobile;
    }

    public LongFilter mobile() {
        if (mobile == null) {
            mobile = new LongFilter();
        }
        return mobile;
    }

    public void setMobile(LongFilter mobile) {
        this.mobile = mobile;
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

    public LongFilter getEmployeeIdId() {
        return employeeIdId;
    }

    public LongFilter employeeIdId() {
        if (employeeIdId == null) {
            employeeIdId = new LongFilter();
        }
        return employeeIdId;
    }

    public void setEmployeeIdId(LongFilter employeeIdId) {
        this.employeeIdId = employeeIdId;
    }

    public LongFilter getEmployeeHisId() {
        return employeeHisId;
    }

    public LongFilter employeeHisId() {
        if (employeeHisId == null) {
            employeeHisId = new LongFilter();
        }
        return employeeHisId;
    }

    public void setEmployeeHisId(LongFilter employeeHisId) {
        this.employeeHisId = employeeHisId;
    }

    public LongFilter getEmployeeServiceNoId() {
        return employeeServiceNoId;
    }

    public LongFilter employeeServiceNoId() {
        if (employeeServiceNoId == null) {
            employeeServiceNoId = new LongFilter();
        }
        return employeeServiceNoId;
    }

    public void setEmployeeServiceNoId(LongFilter employeeServiceNoId) {
        this.employeeServiceNoId = employeeServiceNoId;
    }

    public LongFilter getPatientTestTimingsId() {
        return patientTestTimingsId;
    }

    public LongFilter patientTestTimingsId() {
        if (patientTestTimingsId == null) {
            patientTestTimingsId = new LongFilter();
        }
        return patientTestTimingsId;
    }

    public void setPatientTestTimingsId(LongFilter patientTestTimingsId) {
        this.patientTestTimingsId = patientTestTimingsId;
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
            Objects.equals(name, that.name) &&
            Objects.equals(age, that.age) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(dateOfBirth, that.dateOfBirth) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(relation, that.relation) &&
            Objects.equals(employeeIdId, that.employeeIdId) &&
            Objects.equals(employeeHisId, that.employeeHisId) &&
            Objects.equals(employeeServiceNoId, that.employeeServiceNoId) &&
            Objects.equals(patientTestTimingsId, that.patientTestTimingsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            age,
            gender,
            dateOfBirth,
            mobile,
            relation,
            employeeIdId,
            employeeHisId,
            employeeServiceNoId,
            patientTestTimingsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientInfoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (age != null ? "age=" + age + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "") +
            (mobile != null ? "mobile=" + mobile + ", " : "") +
            (relation != null ? "relation=" + relation + ", " : "") +
            (employeeIdId != null ? "employeeIdId=" + employeeIdId + ", " : "") +
            (employeeHisId != null ? "employeeHisId=" + employeeHisId + ", " : "") +
            (employeeServiceNoId != null ? "employeeServiceNoId=" + employeeServiceNoId + ", " : "") +
            (patientTestTimingsId != null ? "patientTestTimingsId=" + patientTestTimingsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
