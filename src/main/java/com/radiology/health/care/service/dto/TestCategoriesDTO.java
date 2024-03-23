package com.radiology.health.care.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.radiology.health.care.domain.TestCategories} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TestCategoriesDTO implements Serializable {

    private Long id;

    @NotNull
    private String testName;

    private Long equipmentId;

    private Long parentTestCategoryId;

    private Long userId;

    private String login;
    private String createdBy;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Long getParentTestCategoryId() {
        return parentTestCategoryId;
    }

    public void setParentTestCategoryId(Long parentTestCategoryId) {
        this.parentTestCategoryId = parentTestCategoryId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setTestName(String testName) {
        this.testName = testName;
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
            ", equipmentId=" + getEquipmentId() +
            ", parentTestCategoryId=" + getParentTestCategoryId() +
            ", userId=" + getUserId() +
            "}";
    }
}
