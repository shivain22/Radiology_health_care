package com.radiology.health.care.web.rest;

import com.radiology.health.care.repository.TestCategoriesRepository;
import com.radiology.health.care.service.TestCategoriesQueryService;
import com.radiology.health.care.service.TestCategoriesService;
import com.radiology.health.care.service.criteria.TestCategoriesCriteria;
import com.radiology.health.care.service.dto.TestCategoriesDTO;
import com.radiology.health.care.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.radiology.health.care.domain.TestCategories}.
 */
@RestController
@RequestMapping("/api/test-categories")
public class TestCategoriesResource {

    private final Logger log = LoggerFactory.getLogger(TestCategoriesResource.class);

    private static final String ENTITY_NAME = "testCategories";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TestCategoriesService testCategoriesService;

    private final TestCategoriesRepository testCategoriesRepository;

    private final TestCategoriesQueryService testCategoriesQueryService;

    public TestCategoriesResource(
        TestCategoriesService testCategoriesService,
        TestCategoriesRepository testCategoriesRepository,
        TestCategoriesQueryService testCategoriesQueryService
    ) {
        this.testCategoriesService = testCategoriesService;
        this.testCategoriesRepository = testCategoriesRepository;
        this.testCategoriesQueryService = testCategoriesQueryService;
    }

    /**
     * {@code POST  /test-categories} : Create a new testCategories.
     *
     * @param testCategoriesDTO the testCategoriesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new testCategoriesDTO, or with status {@code 400 (Bad Request)} if the testCategories has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TestCategoriesDTO> createTestCategories(@Valid @RequestBody TestCategoriesDTO testCategoriesDTO)
        throws URISyntaxException {
        log.debug("REST request to save TestCategories : {}", testCategoriesDTO);
        if (testCategoriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new testCategories cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TestCategoriesDTO result = testCategoriesService.save(testCategoriesDTO);
        return ResponseEntity
            .created(new URI("/api/test-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /test-categories/:id} : Updates an existing testCategories.
     *
     * @param id the id of the testCategoriesDTO to save.
     * @param testCategoriesDTO the testCategoriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated testCategoriesDTO,
     * or with status {@code 400 (Bad Request)} if the testCategoriesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the testCategoriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TestCategoriesDTO> updateTestCategories(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TestCategoriesDTO testCategoriesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TestCategories : {}, {}", id, testCategoriesDTO);
        if (testCategoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, testCategoriesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!testCategoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TestCategoriesDTO result = testCategoriesService.update(testCategoriesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, testCategoriesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /test-categories/:id} : Partial updates given fields of an existing testCategories, field will ignore if it is null
     *
     * @param id the id of the testCategoriesDTO to save.
     * @param testCategoriesDTO the testCategoriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated testCategoriesDTO,
     * or with status {@code 400 (Bad Request)} if the testCategoriesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the testCategoriesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the testCategoriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TestCategoriesDTO> partialUpdateTestCategories(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TestCategoriesDTO testCategoriesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TestCategories partially : {}, {}", id, testCategoriesDTO);
        if (testCategoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, testCategoriesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!testCategoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TestCategoriesDTO> result = testCategoriesService.partialUpdate(testCategoriesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, testCategoriesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /test-categories} : get all the testCategories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of testCategories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TestCategoriesDTO>> getAllTestCategories(
        TestCategoriesCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TestCategories by criteria: {}", criteria);

        Page<TestCategoriesDTO> page = testCategoriesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /test-categories/count} : count all the testCategories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTestCategories(TestCategoriesCriteria criteria) {
        log.debug("REST request to count TestCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(testCategoriesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /test-categories/:id} : get the "id" testCategories.
     *
     * @param id the id of the testCategoriesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the testCategoriesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TestCategoriesDTO> getTestCategories(@PathVariable("id") Long id) {
        log.debug("REST request to get TestCategories : {}", id);
        Optional<TestCategoriesDTO> testCategoriesDTO = testCategoriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(testCategoriesDTO);
    }

    /**
     * {@code DELETE  /test-categories/:id} : delete the "id" testCategories.
     *
     * @param id the id of the testCategoriesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestCategories(@PathVariable("id") Long id) {
        log.debug("REST request to delete TestCategories : {}", id);
        testCategoriesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
