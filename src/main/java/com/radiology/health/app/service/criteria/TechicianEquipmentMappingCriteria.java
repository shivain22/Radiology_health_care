package com.radiology.health.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.radiology.health.app.domain.TechicianEquipmentMapping} entity. This class is used
 * in {@link com.radiology.health.app.web.rest.TechicianEquipmentMappingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /techician-equipment-mappings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TechicianEquipmentMappingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter dateTime;

    private LongFilter employeeId;

    private LongFilter equipmentsId;

    private Boolean distinct;

    public TechicianEquipmentMappingCriteria() {}

    public TechicianEquipmentMappingCriteria(TechicianEquipmentMappingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateTime = other.dateTime == null ? null : other.dateTime.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.equipmentsId = other.equipmentsId == null ? null : other.equipmentsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TechicianEquipmentMappingCriteria copy() {
        return new TechicianEquipmentMappingCriteria(this);
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

    public StringFilter getDateTime() {
        return dateTime;
    }

    public StringFilter dateTime() {
        if (dateTime == null) {
            dateTime = new StringFilter();
        }
        return dateTime;
    }

    public void setDateTime(StringFilter dateTime) {
        this.dateTime = dateTime;
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
        final TechicianEquipmentMappingCriteria that = (TechicianEquipmentMappingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dateTime, that.dateTime) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(equipmentsId, that.equipmentsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, employeeId, equipmentsId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TechicianEquipmentMappingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dateTime != null ? "dateTime=" + dateTime + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (equipmentsId != null ? "equipmentsId=" + equipmentsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
