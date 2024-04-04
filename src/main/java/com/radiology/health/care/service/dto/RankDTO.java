package com.radiology.health.care.service.dto;

import com.radiology.health.care.domain.enumeration.rankDivisions;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.radiology.health.care.domain.Rank} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RankDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String shortName;

    private rankDivisions division;

    private Long empServiceId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public rankDivisions getDivision() {
        return division;
    }

    public void setDivision(rankDivisions division) {
        this.division = division;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RankDTO)) {
            return false;
        }

        RankDTO rankDTO = (RankDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rankDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    public Long getEmpServiceId() {
        return empServiceId;
    }

    public void setEmpServiceId(Long empServiceId) {
        this.empServiceId = empServiceId;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RankDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", division='" + getDivision() + "'" +
            ", empServiceId=" + getEmpServiceId() +
            ", userId=" + getUserId() +
            "}";
    }
}
