package com.radiology.health.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.radiology.health.app.domain.TestCatogories} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TestCatogoriesDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private EquipmentsDTO equipments;

    private TestCatogoriesDTO testCatogories_parent;

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

    public EquipmentsDTO getEquipments() {
        return equipments;
    }

    public void setEquipments(EquipmentsDTO equipments) {
        this.equipments = equipments;
    }

    public TestCatogoriesDTO getTestCatogories_parent() {
        return testCatogories_parent;
    }

    public void setTestCatogories_parent(TestCatogoriesDTO testCatogories_parent) {
        this.testCatogories_parent = testCatogories_parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TestCatogoriesDTO)) {
            return false;
        }

        TestCatogoriesDTO testCatogoriesDTO = (TestCatogoriesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, testCatogoriesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TestCatogoriesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", equipments=" + getEquipments() +
            ", testCatogories_parent=" + getTestCatogories_parent() +
            "}";
    }
}
