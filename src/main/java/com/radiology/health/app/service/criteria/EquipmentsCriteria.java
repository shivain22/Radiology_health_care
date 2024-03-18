package com.radiology.health.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.radiology.health.app.domain.Equipments} entity. This class is used
 * in {@link com.radiology.health.app.web.rest.EquipmentsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /equipments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EquipmentsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter roomsId;

    private LongFilter testCatogoriesId;

    private LongFilter techicianEquipmentMappingId;

    private Boolean distinct;

    public EquipmentsCriteria() {}

    public EquipmentsCriteria(EquipmentsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.roomsId = other.roomsId == null ? null : other.roomsId.copy();
        this.testCatogoriesId = other.testCatogoriesId == null ? null : other.testCatogoriesId.copy();
        this.techicianEquipmentMappingId = other.techicianEquipmentMappingId == null ? null : other.techicianEquipmentMappingId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EquipmentsCriteria copy() {
        return new EquipmentsCriteria(this);
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

    public LongFilter getRoomsId() {
        return roomsId;
    }

    public LongFilter roomsId() {
        if (roomsId == null) {
            roomsId = new LongFilter();
        }
        return roomsId;
    }

    public void setRoomsId(LongFilter roomsId) {
        this.roomsId = roomsId;
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

    public LongFilter getTechicianEquipmentMappingId() {
        return techicianEquipmentMappingId;
    }

    public LongFilter techicianEquipmentMappingId() {
        if (techicianEquipmentMappingId == null) {
            techicianEquipmentMappingId = new LongFilter();
        }
        return techicianEquipmentMappingId;
    }

    public void setTechicianEquipmentMappingId(LongFilter techicianEquipmentMappingId) {
        this.techicianEquipmentMappingId = techicianEquipmentMappingId;
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
        final EquipmentsCriteria that = (EquipmentsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(roomsId, that.roomsId) &&
            Objects.equals(testCatogoriesId, that.testCatogoriesId) &&
            Objects.equals(techicianEquipmentMappingId, that.techicianEquipmentMappingId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, roomsId, testCatogoriesId, techicianEquipmentMappingId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EquipmentsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (roomsId != null ? "roomsId=" + roomsId + ", " : "") +
            (testCatogoriesId != null ? "testCatogoriesId=" + testCatogoriesId + ", " : "") +
            (techicianEquipmentMappingId != null ? "techicianEquipmentMappingId=" + techicianEquipmentMappingId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
