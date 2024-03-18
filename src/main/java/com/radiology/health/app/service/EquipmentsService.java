package com.radiology.health.app.service;

import com.radiology.health.app.service.dto.EquipmentsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.radiology.health.app.domain.Equipments}.
 */
public interface EquipmentsService {
    /**
     * Save a equipments.
     *
     * @param equipmentsDTO the entity to save.
     * @return the persisted entity.
     */
    EquipmentsDTO save(EquipmentsDTO equipmentsDTO);

    /**
     * Updates a equipments.
     *
     * @param equipmentsDTO the entity to update.
     * @return the persisted entity.
     */
    EquipmentsDTO update(EquipmentsDTO equipmentsDTO);

    /**
     * Partially updates a equipments.
     *
     * @param equipmentsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EquipmentsDTO> partialUpdate(EquipmentsDTO equipmentsDTO);

    /**
     * Get all the equipments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EquipmentsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" equipments.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EquipmentsDTO> findOne(Long id);

    /**
     * Delete the "id" equipments.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
