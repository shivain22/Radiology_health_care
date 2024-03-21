package com.radiology.health.care.service;

import com.radiology.health.care.service.dto.TestCategoriesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.radiology.health.care.domain.TestCategories}.
 */
public interface TestCategoriesService {
    /**
     * Save a testCategories.
     *
     * @param testCategoriesDTO the entity to save.
     * @return the persisted entity.
     */
    TestCategoriesDTO save(TestCategoriesDTO testCategoriesDTO);

    /**
     * Updates a testCategories.
     *
     * @param testCategoriesDTO the entity to update.
     * @return the persisted entity.
     */
    TestCategoriesDTO update(TestCategoriesDTO testCategoriesDTO);

    /**
     * Partially updates a testCategories.
     *
     * @param testCategoriesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TestCategoriesDTO> partialUpdate(TestCategoriesDTO testCategoriesDTO);

    /**
     * Get all the testCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TestCategoriesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" testCategories.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TestCategoriesDTO> findOne(Long id);

    /**
     * Delete the "id" testCategories.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
