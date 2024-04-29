package com.radiology.health.care.repository;

import com.radiology.health.care.domain.PatientTestTimings;
import com.radiology.health.care.service.dto.PatientTestTimingsDTO;
import com.radiology.health.care.service.dto.TestCategoriesDTO;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PatientTestTimings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientTestTimingsRepository
    extends JpaRepository<PatientTestTimings, Long>, JpaSpecificationExecutor<PatientTestTimings> {
    default PatientTestTimings setEndTiming(PatientTestTimings patientTestTimings, Optional<TestCategoriesDTO> testCategories) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.'Z'");

        ZonedDateTime offsetDateTime = patientTestTimings.getStartTime().withZoneSameInstant(ZoneOffset.ofHoursMinutes(-5, -30));

        ZonedDateTime startTime = ZonedDateTime.parse(offsetDateTime.format(formatter));

        patientTestTimings.setStartTime(startTime);

        TestCategoriesDTO categoriesDTO = testCategories.orElseThrow(() -> new IllegalArgumentException("TestCategoriesDTO is empty"));
        Integer testDuration = categoriesDTO.getTestDuration(); // Assuming testDuration is in hours

        ZonedDateTime endTime = startTime.plusMinutes(testDuration);
        patientTestTimings.setEndTime(endTime);

        return patientTestTimings;
    }
}
