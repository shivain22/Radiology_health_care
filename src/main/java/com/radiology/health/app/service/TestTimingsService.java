package com.radiology.health.app.service;

import com.radiology.health.app.service.dto.TestTimingsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.radiology.health.app.domain.TestTimings}.
 */
public interface TestTimingsService {
    /**
     * Save a testTimings.
     *
     * @param testTimingsDTO the entity to save.
     * @return the persisted entity.
     */
    TestTimingsDTO save(TestTimingsDTO testTimingsDTO);

    /**
     * Updates a testTimings.
     *
     * @param testTimingsDTO the entity to update.
     * @return the persisted entity.
     */
    TestTimingsDTO update(TestTimingsDTO testTimingsDTO);

    /**
     * Partially updates a testTimings.
     *
     * @param testTimingsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TestTimingsDTO> partialUpdate(TestTimingsDTO testTimingsDTO);

    /**
     * Get all the testTimings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TestTimingsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" testTimings.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TestTimingsDTO> findOne(Long id);

    /**
     * Delete the "id" testTimings.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
