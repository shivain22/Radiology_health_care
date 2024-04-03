package com.radiology.health.care.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.radiology.health.care.domain.TestCategories} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TestCategoriesDTO implements Serializable {

    private Long id;

    @NotNull
    private String testName;

    private EquipmentDTO equipment;

    private TestCategoriesDTO parentTestCategory;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public EquipmentDTO getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentDTO equipment) {
        this.equipment = equipment;
    }

    public TestCategoriesDTO getParentTestCategory() {
        return parentTestCategory;
    }

    public void setParentTestCategory(TestCategoriesDTO parentTestCategory) {
        this.parentTestCategory = parentTestCategory;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TestCategoriesDTO)) {
            return false;
        }

        TestCategoriesDTO testCategoriesDTO = (TestCategoriesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, testCategoriesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TestCategoriesDTO{" +
            "id=" + getId() +
            ", testName='" + getTestName() + "'" +
            ", equipment=" + getEquipment() +
            ", parentTestCategory=" + getParentTestCategory() +
            ", user=" + getUser() +
            "}";
    }
}
