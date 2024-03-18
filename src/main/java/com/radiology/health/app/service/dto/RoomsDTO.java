package com.radiology.health.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.radiology.health.app.domain.Rooms} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RoomsDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer roomNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(Integer roomNo) {
        this.roomNo = roomNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoomsDTO)) {
            return false;
        }

        RoomsDTO roomsDTO = (RoomsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, roomsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoomsDTO{" +
            "id=" + getId() +
            ", roomNo=" + getRoomNo() +
            "}";
    }
}
