package com.radiology.health.care.service;

import com.radiology.health.care.service.dto.TechnicianEquipmentMappingDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.radiology.health.care.domain.TechnicianEquipmentMapping}.
 */
public interface TechnicianEquipmentMappingService {
    /**
     * Save a technicianEquipmentMapping.
     *
     * @param technicianEquipmentMappingDTO the entity to save.
     * @return the persisted entity.
     */
    TechnicianEquipmentMappingDTO save(TechnicianEquipmentMappingDTO technicianEquipmentMappingDTO);

    /**
     * Updates a technicianEquipmentMapping.
     *
     * @param technicianEquipmentMappingDTO the entity to update.
     * @return the persisted entity.
     */
    TechnicianEquipmentMappingDTO update(TechnicianEquipmentMappingDTO technicianEquipmentMappingDTO);

    /**
     * Partially updates a technicianEquipmentMapping.
     *
     * @param technicianEquipmentMappingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TechnicianEquipmentMappingDTO> partialUpdate(TechnicianEquipmentMappingDTO technicianEquipmentMappingDTO);

    /**
     * Get all the technicianEquipmentMappings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TechnicianEquipmentMappingDTO> findAll(Pageable pageable);

    /**
     * Get the "id" technicianEquipmentMapping.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TechnicianEquipmentMappingDTO> findOne(Long id);

    /**
     * Delete the "id" technicianEquipmentMapping.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
