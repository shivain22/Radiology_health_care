package com.radiology.health.care.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.radiology.health.care.domain.TechnicianEquipmentMapping} entity. This class is used
 * in {@link com.radiology.health.care.web.rest.TechnicianEquipmentMappingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /technician-equipment-mappings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TechnicianEquipmentMappingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter dateTime;

    private LongFilter equipmentId;

    private LongFilter employeeId;

    private LongFilter userId;

    private Boolean distinct;

    public TechnicianEquipmentMappingCriteria() {}

    public TechnicianEquipmentMappingCriteria(TechnicianEquipmentMappingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateTime = other.dateTime == null ? null : other.dateTime.copy();
        this.equipmentId = other.equipmentId == null ? null : other.equipmentId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TechnicianEquipmentMappingCriteria copy() {
        return new TechnicianEquipmentMappingCriteria(this);
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

    public InstantFilter getDateTime() {
        return dateTime;
    }

    public InstantFilter dateTime() {
        if (dateTime == null) {
            dateTime = new InstantFilter();
        }
        return dateTime;
    }

    public void setDateTime(InstantFilter dateTime) {
        this.dateTime = dateTime;
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
        final TechnicianEquipmentMappingCriteria that = (TechnicianEquipmentMappingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dateTime, that.dateTime) &&
            Objects.equals(equipmentId, that.equipmentId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, equipmentId, employeeId, userId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TechnicianEquipmentMappingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dateTime != null ? "dateTime=" + dateTime + ", " : "") +
            (equipmentId != null ? "equipmentId=" + equipmentId + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
