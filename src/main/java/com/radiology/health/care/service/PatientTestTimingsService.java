package com.radiology.health.care.service;

import com.radiology.health.care.domain.TestCategories;
import com.radiology.health.care.service.dto.PatientTestTimingsDTO;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.radiology.health.care.domain.PatientTestTimings}.
 */
public interface PatientTestTimingsService {
    /**
     * Save a patientTestTimings.
     *
     * @param patientTestTimingsDTO the entity to save.
     * @return the persisted entity.
     */
    PatientTestTimingsDTO save(PatientTestTimingsDTO patientTestTimingsDTO);

    /**
     * Updates a patientTestTimings.
     *
     * @param patientTestTimingsDTO the entity to update.
     * @return the persisted entity.
     */
    PatientTestTimingsDTO update(PatientTestTimingsDTO patientTestTimingsDTO);

    /**
     * Partially updates a patientTestTimings.
     *
     * @param patientTestTimingsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PatientTestTimingsDTO> partialUpdate(PatientTestTimingsDTO patientTestTimingsDTO);

    /**
     * Get all the patientTestTimings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PatientTestTimingsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" patientTestTimings.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PatientTestTimingsDTO> findOne(Long id);

    /**
     * Delete the "id" patientTestTimings.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
