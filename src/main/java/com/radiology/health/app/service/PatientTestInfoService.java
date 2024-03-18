package com.radiology.health.app.service;

import com.radiology.health.app.service.dto.PatientTestInfoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.radiology.health.app.domain.PatientTestInfo}.
 */
public interface PatientTestInfoService {
    /**
     * Save a patientTestInfo.
     *
     * @param patientTestInfoDTO the entity to save.
     * @return the persisted entity.
     */
    PatientTestInfoDTO save(PatientTestInfoDTO patientTestInfoDTO);

    /**
     * Updates a patientTestInfo.
     *
     * @param patientTestInfoDTO the entity to update.
     * @return the persisted entity.
     */
    PatientTestInfoDTO update(PatientTestInfoDTO patientTestInfoDTO);

    /**
     * Partially updates a patientTestInfo.
     *
     * @param patientTestInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PatientTestInfoDTO> partialUpdate(PatientTestInfoDTO patientTestInfoDTO);

    /**
     * Get all the patientTestInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PatientTestInfoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" patientTestInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PatientTestInfoDTO> findOne(Long id);

    /**
     * Delete the "id" patientTestInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
