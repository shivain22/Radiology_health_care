package com.radiology.health.app.service;

import com.radiology.health.app.service.dto.TestCatogoriesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.radiology.health.app.domain.TestCatogories}.
 */
public interface TestCatogoriesService {
    /**
     * Save a testCatogories.
     *
     * @param testCatogoriesDTO the entity to save.
     * @return the persisted entity.
     */
    TestCatogoriesDTO save(TestCatogoriesDTO testCatogoriesDTO);

    /**
     * Updates a testCatogories.
     *
     * @param testCatogoriesDTO the entity to update.
     * @return the persisted entity.
     */
    TestCatogoriesDTO update(TestCatogoriesDTO testCatogoriesDTO);

    /**
     * Partially updates a testCatogories.
     *
     * @param testCatogoriesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TestCatogoriesDTO> partialUpdate(TestCatogoriesDTO testCatogoriesDTO);

    /**
     * Get all the testCatogories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TestCatogoriesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" testCatogories.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TestCatogoriesDTO> findOne(Long id);

    /**
     * Delete the "id" testCatogories.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
