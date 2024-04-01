package com.radiology.health.care.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.radiology.health.care.domain.EmpService} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmpServiceDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

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
        if (!(o instanceof EmpServiceDTO)) {
            return false;
        }

        EmpServiceDTO empServiceDTO = (EmpServiceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, empServiceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpServiceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
