package com.radiology.health.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.radiology.health.app.domain.Rooms} entity. This class is used
 * in {@link com.radiology.health.app.web.rest.RoomsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /rooms?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RoomsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter roomNo;

    private LongFilter equipmentsId;

    private Boolean distinct;

    public RoomsCriteria() {}

    public RoomsCriteria(RoomsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.roomNo = other.roomNo == null ? null : other.roomNo.copy();
        this.equipmentsId = other.equipmentsId == null ? null : other.equipmentsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RoomsCriteria copy() {
        return new RoomsCriteria(this);
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

    public IntegerFilter getRoomNo() {
        return roomNo;
    }

    public IntegerFilter roomNo() {
        if (roomNo == null) {
            roomNo = new IntegerFilter();
        }
        return roomNo;
    }

    public void setRoomNo(IntegerFilter roomNo) {
        this.roomNo = roomNo;
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
        final RoomsCriteria that = (RoomsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(roomNo, that.roomNo) &&
            Objects.equals(equipmentsId, that.equipmentsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomNo, equipmentsId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoomsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (roomNo != null ? "roomNo=" + roomNo + ", " : "") +
            (equipmentsId != null ? "equipmentsId=" + equipmentsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
