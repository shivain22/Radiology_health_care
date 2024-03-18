package com.radiology.health.app.web.rest;

import com.radiology.health.app.repository.TestCatogoriesRepository;
import com.radiology.health.app.service.TestCatogoriesQueryService;
import com.radiology.health.app.service.TestCatogoriesService;
import com.radiology.health.app.service.criteria.TestCatogoriesCriteria;
import com.radiology.health.app.service.dto.TestCatogoriesDTO;
import com.radiology.health.app.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.radiology.health.app.domain.TestCatogories}.
 */
@RestController
@RequestMapping("/api/test-catogories")
public class TestCatogoriesResource {

    private final Logger log = LoggerFactory.getLogger(TestCatogoriesResource.class);

    private static final String ENTITY_NAME = "testCatogories";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TestCatogoriesService testCatogoriesService;

    private final TestCatogoriesRepository testCatogoriesRepository;

    private final TestCatogoriesQueryService testCatogoriesQueryService;

    public TestCatogoriesResource(
        TestCatogoriesService testCatogoriesService,
        TestCatogoriesRepository testCatogoriesRepository,
        TestCatogoriesQueryService testCatogoriesQueryService
    ) {
        this.testCatogoriesService = testCatogoriesService;
        this.testCatogoriesRepository = testCatogoriesRepository;
        this.testCatogoriesQueryService = testCatogoriesQueryService;
    }

    /**
     * {@code POST  /test-catogories} : Create a new testCatogories.
     *
     * @param testCatogoriesDTO the testCatogoriesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new testCatogoriesDTO, or with status {@code 400 (Bad Request)} if the testCatogories has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TestCatogoriesDTO> createTestCatogories(@Valid @RequestBody TestCatogoriesDTO testCatogoriesDTO)
        throws URISyntaxException {
        log.debug("REST request to save TestCatogories : {}", testCatogoriesDTO);
        if (testCatogoriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new testCatogories cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TestCatogoriesDTO result = testCatogoriesService.save(testCatogoriesDTO);
        return ResponseEntity
            .created(new URI("/api/test-catogories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /test-catogories/:id} : Updates an existing testCatogories.
     *
     * @param id the id of the testCatogoriesDTO to save.
     * @param testCatogoriesDTO the testCatogoriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated testCatogoriesDTO,
     * or with status {@code 400 (Bad Request)} if the testCatogoriesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the testCatogoriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TestCatogoriesDTO> updateTestCatogories(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TestCatogoriesDTO testCatogoriesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TestCatogories : {}, {}", id, testCatogoriesDTO);
        if (testCatogoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, testCatogoriesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!testCatogoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TestCatogoriesDTO result = testCatogoriesService.update(testCatogoriesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, testCatogoriesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /test-catogories/:id} : Partial updates given fields of an existing testCatogories, field will ignore if it is null
     *
     * @param id the id of the testCatogoriesDTO to save.
     * @param testCatogoriesDTO the testCatogoriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated testCatogoriesDTO,
     * or with status {@code 400 (Bad Request)} if the testCatogoriesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the testCatogoriesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the testCatogoriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TestCatogoriesDTO> partialUpdateTestCatogories(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TestCatogoriesDTO testCatogoriesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TestCatogories partially : {}, {}", id, testCatogoriesDTO);
        if (testCatogoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, testCatogoriesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!testCatogoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TestCatogoriesDTO> result = testCatogoriesService.partialUpdate(testCatogoriesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, testCatogoriesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /test-catogories} : get all the testCatogories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of testCatogories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TestCatogoriesDTO>> getAllTestCatogories(
        TestCatogoriesCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TestCatogories by criteria: {}", criteria);

        Page<TestCatogoriesDTO> page = testCatogoriesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /test-catogories/count} : count all the testCatogories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTestCatogories(TestCatogoriesCriteria criteria) {
        log.debug("REST request to count TestCatogories by criteria: {}", criteria);
        return ResponseEntity.ok().body(testCatogoriesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /test-catogories/:id} : get the "id" testCatogories.
     *
     * @param id the id of the testCatogoriesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the testCatogoriesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TestCatogoriesDTO> getTestCatogories(@PathVariable("id") Long id) {
        log.debug("REST request to get TestCatogories : {}", id);
        Optional<TestCatogoriesDTO> testCatogoriesDTO = testCatogoriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(testCatogoriesDTO);
    }

    /**
     * {@code DELETE  /test-catogories/:id} : delete the "id" testCatogories.
     *
     * @param id the id of the testCatogoriesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestCatogories(@PathVariable("id") Long id) {
        log.debug("REST request to delete TestCatogories : {}", id);
        testCatogoriesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
