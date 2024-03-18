package com.radiology.health.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.radiology.health.app.domain.Equipments} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EquipmentsDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private RoomsDTO rooms;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomsDTO getRooms() {
        return rooms;
    }

    public void setRooms(RoomsDTO rooms) {
        this.rooms = rooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EquipmentsDTO)) {
            return false;
        }

        EquipmentsDTO equipmentsDTO = (EquipmentsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, equipmentsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EquipmentsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", rooms=" + getRooms() +
            "}";
    }
}
