package com.radiology.health.care.service;

import com.radiology.health.care.service.dto.EmpServiceDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.radiology.health.care.domain.EmpService}.
 */
public interface EmpServiceService {
    /**
     * Save a empService.
     *
     * @param empServiceDTO the entity to save.
     * @return the persisted entity.
     */
    EmpServiceDTO save(EmpServiceDTO empServiceDTO);

    /**
     * Updates a empService.
     *
     * @param empServiceDTO the entity to update.
     * @return the persisted entity.
     */
    EmpServiceDTO update(EmpServiceDTO empServiceDTO);

    /**
     * Partially updates a empService.
     *
     * @param empServiceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EmpServiceDTO> partialUpdate(EmpServiceDTO empServiceDTO);

    /**
     * Get all the empServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmpServiceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" empService.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmpServiceDTO> findOne(Long id);

    /**
     * Delete the "id" empService.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
