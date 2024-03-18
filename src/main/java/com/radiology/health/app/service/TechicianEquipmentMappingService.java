package com.radiology.health.app.service;

import com.radiology.health.app.service.dto.TechicianEquipmentMappingDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.radiology.health.app.domain.TechicianEquipmentMapping}.
 */
public interface TechicianEquipmentMappingService {
    /**
     * Save a techicianEquipmentMapping.
     *
     * @param techicianEquipmentMappingDTO the entity to save.
     * @return the persisted entity.
     */
    TechicianEquipmentMappingDTO save(TechicianEquipmentMappingDTO techicianEquipmentMappingDTO);

    /**
     * Updates a techicianEquipmentMapping.
     *
     * @param techicianEquipmentMappingDTO the entity to update.
     * @return the persisted entity.
     */
    TechicianEquipmentMappingDTO update(TechicianEquipmentMappingDTO techicianEquipmentMappingDTO);

    /**
     * Partially updates a techicianEquipmentMapping.
     *
     * @param techicianEquipmentMappingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TechicianEquipmentMappingDTO> partialUpdate(TechicianEquipmentMappingDTO techicianEquipmentMappingDTO);

    /**
     * Get all the techicianEquipmentMappings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TechicianEquipmentMappingDTO> findAll(Pageable pageable);

    /**
     * Get the "id" techicianEquipmentMapping.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TechicianEquipmentMappingDTO> findOne(Long id);

    /**
     * Delete the "id" techicianEquipmentMapping.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
