package com.radiology.health.care.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.radiology.health.care.domain.Room} entity. This class is used
 * in {@link com.radiology.health.care.web.rest.RoomResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /rooms?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RoomCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter roomNo;

    private LongFilter userId;

    private LongFilter equipmentId;

    private Boolean distinct;

    public RoomCriteria() {}

    public RoomCriteria(RoomCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.roomNo = other.roomNo == null ? null : other.roomNo.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.equipmentId = other.equipmentId == null ? null : other.equipmentId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RoomCriteria copy() {
        return new RoomCriteria(this);
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
        final RoomCriteria that = (RoomCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(roomNo, that.roomNo) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(equipmentId, that.equipmentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomNo, userId, equipmentId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoomCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (roomNo != null ? "roomNo=" + roomNo + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (equipmentId != null ? "equipmentId=" + equipmentId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
