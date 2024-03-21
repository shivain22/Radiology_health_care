package com.radiology.health.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Room.
 */
@Entity
@Table(name = "room")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "room_no", nullable = false)
    private Integer roomNo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    @JsonIgnoreProperties(value = { "room", "technicianEquipmentMappings", "testCategories" }, allowSetters = true)
    private Set<Equipment> equipment = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Room id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRoomNo() {
        return this.roomNo;
    }

    public Room roomNo(Integer roomNo) {
        this.setRoomNo(roomNo);
        return this;
    }

    public void setRoomNo(Integer roomNo) {
        this.roomNo = roomNo;
    }

    public Set<Equipment> getEquipment() {
        return this.equipment;
    }

    public void setEquipment(Set<Equipment> equipment) {
        if (this.equipment != null) {
            this.equipment.forEach(i -> i.setRoom(null));
        }
        if (equipment != null) {
            equipment.forEach(i -> i.setRoom(this));
        }
        this.equipment = equipment;
    }

    public Room equipment(Set<Equipment> equipment) {
        this.setEquipment(equipment);
        return this;
    }

    public Room addEquipment(Equipment equipment) {
        this.equipment.add(equipment);
        equipment.setRoom(this);
        return this;
    }

    public Room removeEquipment(Equipment equipment) {
        this.equipment.remove(equipment);
        equipment.setRoom(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Room)) {
            return false;
        }
        return getId() != null && getId().equals(((Room) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Room{" +
            "id=" + getId() +
            ", roomNo=" + getRoomNo() +
            "}";
    }
}
