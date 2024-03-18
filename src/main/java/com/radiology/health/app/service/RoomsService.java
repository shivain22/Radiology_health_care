package com.radiology.health.app.service;

import com.radiology.health.app.service.dto.RoomsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.radiology.health.app.domain.Rooms}.
 */
public interface RoomsService {
    /**
     * Save a rooms.
     *
     * @param roomsDTO the entity to save.
     * @return the persisted entity.
     */
    RoomsDTO save(RoomsDTO roomsDTO);

    /**
     * Updates a rooms.
     *
     * @param roomsDTO the entity to update.
     * @return the persisted entity.
     */
    RoomsDTO update(RoomsDTO roomsDTO);

    /**
     * Partially updates a rooms.
     *
     * @param roomsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RoomsDTO> partialUpdate(RoomsDTO roomsDTO);

    /**
     * Get all the rooms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RoomsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" rooms.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RoomsDTO> findOne(Long id);

    /**
     * Delete the "id" rooms.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
