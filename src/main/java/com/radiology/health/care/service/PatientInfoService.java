package com.radiology.health.care.service;

import com.radiology.health.care.service.dto.PatientInfoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.radiology.health.care.domain.PatientInfo}.
 */
public interface PatientInfoService {
    /**
     * Save a patientInfo.
     *
     * @param patientInfoDTO the entity to save.
     * @return the persisted entity.
     */
    PatientInfoDTO save(PatientInfoDTO patientInfoDTO);

    /**
     * Updates a patientInfo.
     *
     * @param patientInfoDTO the entity to update.
     * @return the persisted entity.
     */
    PatientInfoDTO update(PatientInfoDTO patientInfoDTO);

    /**
     * Partially updates a patientInfo.
     *
     * @param patientInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PatientInfoDTO> partialUpdate(PatientInfoDTO patientInfoDTO);

    /**
     * Get all the patientInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PatientInfoDTO> findAll(Pageable pageable);

    /**
     * Get all the patientInfos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PatientInfoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" patientInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PatientInfoDTO> findOne(Long id);

    /**
     * Delete the "id" patientInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
