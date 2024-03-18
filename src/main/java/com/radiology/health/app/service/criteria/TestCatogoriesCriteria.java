package com.radiology.health.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.radiology.health.app.domain.TestCatogories} entity. This class is used
 * in {@link com.radiology.health.app.web.rest.TestCatogoriesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /test-catogories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TestCatogoriesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter equipmentsId;

    private LongFilter testCatogories_parentId;

    private LongFilter test_catogories_parent_catogoryId;

    private LongFilter testTimingsId;

    private Boolean distinct;

    public TestCatogoriesCriteria() {}

    public TestCatogoriesCriteria(TestCatogoriesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.equipmentsId = other.equipmentsId == null ? null : other.equipmentsId.copy();
        this.testCatogories_parentId = other.testCatogories_parentId == null ? null : other.testCatogories_parentId.copy();
        this.test_catogories_parent_catogoryId =
            other.test_catogories_parent_catogoryId == null ? null : other.test_catogories_parent_catogoryId.copy();
        this.testTimingsId = other.testTimingsId == null ? null : other.testTimingsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TestCatogoriesCriteria copy() {
        return new TestCatogoriesCriteria(this);
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

    public LongFilter getEquipmentsId() {
        return equipmentsId;
    }

    public LongFilter equipmentsId() {
        if (equipmentsId == null) {
            equipmentsId = new LongFilter();
        }
        return equipmentsId;
    }

    public void setEquipmentsId(LongFilter equipmentsId) {
        this.equipmentsId = equipmentsId;
    }

    public LongFilter getTestCatogories_parentId() {
        return testCatogories_parentId;
    }

    public LongFilter testCatogories_parentId() {
        if (testCatogories_parentId == null) {
            testCatogories_parentId = new LongFilter();
        }
        return testCatogories_parentId;
    }

    public void setTestCatogories_parentId(LongFilter testCatogories_parentId) {
        this.testCatogories_parentId = testCatogories_parentId;
    }

    public LongFilter getTest_catogories_parent_catogoryId() {
        return test_catogories_parent_catogoryId;
    }

    public LongFilter test_catogories_parent_catogoryId() {
        if (test_catogories_parent_catogoryId == null) {
            test_catogories_parent_catogoryId = new LongFilter();
        }
        return test_catogories_parent_catogoryId;
    }

    public void setTest_catogories_parent_catogoryId(LongFilter test_catogories_parent_catogoryId) {
        this.test_catogories_parent_catogoryId = test_catogories_parent_catogoryId;
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
        final TestCatogoriesCriteria that = (TestCatogoriesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(equipmentsId, that.equipmentsId) &&
            Objects.equals(testCatogories_parentId, that.testCatogories_parentId) &&
            Objects.equals(test_catogories_parent_catogoryId, that.test_catogories_parent_catogoryId) &&
            Objects.equals(testTimingsId, that.testTimingsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, equipmentsId, testCatogories_parentId, test_catogories_parent_catogoryId, testTimingsId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TestCatogoriesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (equipmentsId != null ? "equipmentsId=" + equipmentsId + ", " : "") +
            (testCatogories_parentId != null ? "testCatogories_parentId=" + testCatogories_parentId + ", " : "") +
            (test_catogories_parent_catogoryId != null ? "test_catogories_parent_catogoryId=" + test_catogories_parent_catogoryId + ", " : "") +
            (testTimingsId != null ? "testTimingsId=" + testTimingsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
