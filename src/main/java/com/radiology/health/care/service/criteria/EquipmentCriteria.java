package com.radiology.health.care.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.radiology.health.care.domain.Equipment} entity. This class is used
 * in {@link com.radiology.health.care.web.rest.EquipmentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /equipment?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EquipmentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter roomId;

    private LongFilter technicianEquipmentMappingId;

    private LongFilter testCategoriesId;

    private Boolean distinct;

    public EquipmentCriteria() {}

    public EquipmentCriteria(EquipmentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.roomId = other.roomId == null ? null : other.roomId.copy();
        this.technicianEquipmentMappingId = other.technicianEquipmentMappingId == null ? null : other.technicianEquipmentMappingId.copy();
        this.testCategoriesId = other.testCategoriesId == null ? null : other.testCategoriesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EquipmentCriteria copy() {
        return new EquipmentCriteria(this);
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

    public LongFilter getRoomId() {
        return roomId;
    }

    public LongFilter roomId() {
        if (roomId == null) {
            roomId = new LongFilter();
        }
        return roomId;
    }

    public void setRoomId(LongFilter roomId) {
        this.roomId = roomId;
    }

    public LongFilter getTechnicianEquipmentMappingId() {
        return technicianEquipmentMappingId;
    }

    public LongFilter technicianEquipmentMappingId() {
        if (technicianEquipmentMappingId == null) {
            technicianEquipmentMappingId = new LongFilter();
        }
        return technicianEquipmentMappingId;
    }

    public void setTechnicianEquipmentMappingId(LongFilter technicianEquipmentMappingId) {
        this.technicianEquipmentMappingId = technicianEquipmentMappingId;
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
        final EquipmentCriteria that = (EquipmentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(roomId, that.roomId) &&
            Objects.equals(technicianEquipmentMappingId, that.technicianEquipmentMappingId) &&
            Objects.equals(testCategoriesId, that.testCategoriesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, roomId, technicianEquipmentMappingId, testCategoriesId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EquipmentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (roomId != null ? "roomId=" + roomId + ", " : "") +
            (technicianEquipmentMappingId != null ? "technicianEquipmentMappingId=" + technicianEquipmentMappingId + ", " : "") +
            (testCategoriesId != null ? "testCategoriesId=" + testCategoriesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
