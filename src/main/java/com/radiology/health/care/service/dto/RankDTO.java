package com.radiology.health.care.service.dto;

import com.radiology.health.care.domain.enumeration.rankDivisions;
import jakarta.validation.constraints.*;
import java.io.Serializable;
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

    private EmpServiceDTO empService;

    private UserDTO user;

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

    public EmpServiceDTO getEmpService() {
        return empService;
    }

    public void setEmpService(EmpServiceDTO empService) {
        this.empService = empService;
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

    // prettier-ignore
    @Override
    public String toString() {
        return "RankDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", division='" + getDivision() + "'" +
            ", empService=" + getEmpService() +
            ", user=" + getUser() +
            "}";
    }
}
