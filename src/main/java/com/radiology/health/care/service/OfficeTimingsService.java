package com.radiology.health.care.service;

import com.radiology.health.care.service.dto.OfficeTimingsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.radiology.health.care.domain.OfficeTimings}.
 */
public interface OfficeTimingsService {
    /**
     * Save a officeTimings.
     *
     * @param officeTimingsDTO the entity to save.
     * @return the persisted entity.
     */
    OfficeTimingsDTO save(OfficeTimingsDTO officeTimingsDTO);

    /**
     * Updates a officeTimings.
     *
     * @param officeTimingsDTO the entity to update.
     * @return the persisted entity.
     */
    OfficeTimingsDTO update(OfficeTimingsDTO officeTimingsDTO);

    /**
     * Partially updates a officeTimings.
     *
     * @param officeTimingsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OfficeTimingsDTO> partialUpdate(OfficeTimingsDTO officeTimingsDTO);

    /**
     * Get all the officeTimings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OfficeTimingsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" officeTimings.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OfficeTimingsDTO> findOne(Long id);

    /**
     * Delete the "id" officeTimings.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
