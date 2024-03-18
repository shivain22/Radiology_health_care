package com.radiology.health.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Rooms.
 */
@Entity
@Table(name = "rooms")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Rooms implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "room_no", nullable = false, unique = true)
    private Integer roomNo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rooms")
    @JsonIgnoreProperties(value = { "rooms", "testCatogories", "techicianEquipmentMappings" }, allowSetters = true)
    private Set<Equipments> equipments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rooms id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRoomNo() {
        return this.roomNo;
    }

    public Rooms roomNo(Integer roomNo) {
        this.setRoomNo(roomNo);
        return this;
    }

    public void setRoomNo(Integer roomNo) {
        this.roomNo = roomNo;
    }

    public Set<Equipments> getEquipments() {
        return this.equipments;
    }

    public void setEquipments(Set<Equipments> equipments) {
        if (this.equipments != null) {
            this.equipments.forEach(i -> i.setRooms(null));
        }
        if (equipments != null) {
            equipments.forEach(i -> i.setRooms(this));
        }
        this.equipments = equipments;
    }

    public Rooms equipments(Set<Equipments> equipments) {
        this.setEquipments(equipments);
        return this;
    }

    public Rooms addEquipments(Equipments equipments) {
        this.equipments.add(equipments);
        equipments.setRooms(this);
        return this;
    }

    public Rooms removeEquipments(Equipments equipments) {
        this.equipments.remove(equipments);
        equipments.setRooms(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rooms)) {
            return false;
        }
        return getId() != null && getId().equals(((Rooms) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rooms{" +
            "id=" + getId() +
            ", roomNo=" + getRoomNo() +
            "}";
    }
}
