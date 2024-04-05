package com.radiology.health.care.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.radiology.health.care.domain.TestCategories} entity. This class is used
 * in {@link com.radiology.health.care.web.rest.TestCategoriesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /test-categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TestCategoriesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter testName;

    private IntegerFilter testDuration;

    private LongFilter equipmentId;

    private LongFilter parentTestCategoryId;

    private LongFilter userId;

    private LongFilter patientTestTimingsId;

    private LongFilter testCategoryParentId;

    private Boolean distinct;

    public TestCategoriesCriteria() {}

    public TestCategoriesCriteria(TestCategoriesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.testName = other.testName == null ? null : other.testName.copy();
        this.testDuration = other.testDuration == null ? null : other.testDuration.copy();
        this.equipmentId = other.equipmentId == null ? null : other.equipmentId.copy();
        this.parentTestCategoryId = other.parentTestCategoryId == null ? null : other.parentTestCategoryId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.patientTestTimingsId = other.patientTestTimingsId == null ? null : other.patientTestTimingsId.copy();
        this.testCategoryParentId = other.testCategoryParentId == null ? null : other.testCategoryParentId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TestCategoriesCriteria copy() {
        return new TestCategoriesCriteria(this);
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

    public StringFilter getTestName() {
        return testName;
    }

    public StringFilter testName() {
        if (testName == null) {
            testName = new StringFilter();
        }
        return testName;
    }

    public void setTestName(StringFilter testName) {
        this.testName = testName;
    }

    public IntegerFilter getTestDuration() {
        return testDuration;
    }

    public IntegerFilter testDuration() {
        if (testDuration == null) {
            testDuration = new IntegerFilter();
        }
        return testDuration;
    }

    public void setTestDuration(IntegerFilter testDuration) {
        this.testDuration = testDuration;
    }

    public LongFilter getEquipmentId() {
        return equipmentId;
    }

    public LongFilter equipmentId() {
        if (equipmentId == null) {
            equipmentId = new LongFilter();
        }
        return equipmentId;
    }

    public void setEquipmentId(LongFilter equipmentId) {
        this.equipmentId = equipmentId;
    }

    public LongFilter getParentTestCategoryId() {
        return parentTestCategoryId;
    }

    public LongFilter parentTestCategoryId() {
        if (parentTestCategoryId == null) {
            parentTestCategoryId = new LongFilter();
        }
        return parentTestCategoryId;
    }

    public void setParentTestCategoryId(LongFilter parentTestCategoryId) {
        this.parentTestCategoryId = parentTestCategoryId;
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

    public LongFilter getTestCategoryParentId() {
        return testCategoryParentId;
    }

    public LongFilter testCategoryParentId() {
        if (testCategoryParentId == null) {
            testCategoryParentId = new LongFilter();
        }
        return testCategoryParentId;
    }

    public void setTestCategoryParentId(LongFilter testCategoryParentId) {
        this.testCategoryParentId = testCategoryParentId;
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
        final TestCategoriesCriteria that = (TestCategoriesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(testName, that.testName) &&
            Objects.equals(testDuration, that.testDuration) &&
            Objects.equals(equipmentId, that.equipmentId) &&
            Objects.equals(parentTestCategoryId, that.parentTestCategoryId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(patientTestTimingsId, that.patientTestTimingsId) &&
            Objects.equals(testCategoryParentId, that.testCategoryParentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            testName,
            testDuration,
            equipmentId,
            parentTestCategoryId,
            userId,
            patientTestTimingsId,
            testCategoryParentId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TestCategoriesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (testName != null ? "testName=" + testName + ", " : "") +
            (testDuration != null ? "testDuration=" + testDuration + ", " : "") +
            (equipmentId != null ? "equipmentId=" + equipmentId + ", " : "") +
            (parentTestCategoryId != null ? "parentTestCategoryId=" + parentTestCategoryId + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (patientTestTimingsId != null ? "patientTestTimingsId=" + patientTestTimingsId + ", " : "") +
            (testCategoryParentId != null ? "testCategoryParentId=" + testCategoryParentId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
