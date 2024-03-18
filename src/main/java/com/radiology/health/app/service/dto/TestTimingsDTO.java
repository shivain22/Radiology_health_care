package com.radiology.health.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.radiology.health.app.domain.TestTimings} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TestTimingsDTO implements Serializable {

    private Long id;

    @NotNull
    private String timings;

    private TestCatogoriesDTO testCatogories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public TestCatogoriesDTO getTestCatogories() {
        return testCatogories;
    }

    public void setTestCatogories(TestCatogoriesDTO testCatogories) {
        this.testCatogories = testCatogories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TestTimingsDTO)) {
            return false;
        }

        TestTimingsDTO testTimingsDTO = (TestTimingsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, testTimingsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TestTimingsDTO{" +
            "id=" + getId() +
            ", timings='" + getTimings() + "'" +
            ", testCatogories=" + getTestCatogories() +
            "}";
    }
}
