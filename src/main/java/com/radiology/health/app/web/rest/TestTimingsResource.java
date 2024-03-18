package com.radiology.health.app.web.rest;

import com.radiology.health.app.repository.TestTimingsRepository;
import com.radiology.health.app.service.TestTimingsQueryService;
import com.radiology.health.app.service.TestTimingsService;
import com.radiology.health.app.service.criteria.TestTimingsCriteria;
import com.radiology.health.app.service.dto.TestTimingsDTO;
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
 * REST controller for managing {@link com.radiology.health.app.domain.TestTimings}.
 */
@RestController
@RequestMapping("/api/test-timings")
public class TestTimingsResource {

    private final Logger log = LoggerFactory.getLogger(TestTimingsResource.class);

    private static final String ENTITY_NAME = "testTimings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TestTimingsService testTimingsService;

    private final TestTimingsRepository testTimingsRepository;

    private final TestTimingsQueryService testTimingsQueryService;

    public TestTimingsResource(
        TestTimingsService testTimingsService,
        TestTimingsRepository testTimingsRepository,
        TestTimingsQueryService testTimingsQueryService
    ) {
        this.testTimingsService = testTimingsService;
        this.testTimingsRepository = testTimingsRepository;
        this.testTimingsQueryService = testTimingsQueryService;
    }

    /**
     * {@code POST  /test-timings} : Create a new testTimings.
     *
     * @param testTimingsDTO the testTimingsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new testTimingsDTO, or with status {@code 400 (Bad Request)} if the testTimings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TestTimingsDTO> createTestTimings(@Valid @RequestBody TestTimingsDTO testTimingsDTO) throws URISyntaxException {
        log.debug("REST request to save TestTimings : {}", testTimingsDTO);
        if (testTimingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new testTimings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TestTimingsDTO result = testTimingsService.save(testTimingsDTO);
        return ResponseEntity
            .created(new URI("/api/test-timings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /test-timings/:id} : Updates an existing testTimings.
     *
     * @param id the id of the testTimingsDTO to save.
     * @param testTimingsDTO the testTimingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated testTimingsDTO,
     * or with status {@code 400 (Bad Request)} if the testTimingsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the testTimingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TestTimingsDTO> updateTestTimings(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TestTimingsDTO testTimingsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TestTimings : {}, {}", id, testTimingsDTO);
        if (testTimingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, testTimingsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!testTimingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TestTimingsDTO result = testTimingsService.update(testTimingsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, testTimingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /test-timings/:id} : Partial updates given fields of an existing testTimings, field will ignore if it is null
     *
     * @param id the id of the testTimingsDTO to save.
     * @param testTimingsDTO the testTimingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated testTimingsDTO,
     * or with status {@code 400 (Bad Request)} if the testTimingsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the testTimingsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the testTimingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TestTimingsDTO> partialUpdateTestTimings(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TestTimingsDTO testTimingsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TestTimings partially : {}, {}", id, testTimingsDTO);
        if (testTimingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, testTimingsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!testTimingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TestTimingsDTO> result = testTimingsService.partialUpdate(testTimingsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, testTimingsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /test-timings} : get all the testTimings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of testTimings in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TestTimingsDTO>> getAllTestTimings(
        TestTimingsCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TestTimings by criteria: {}", criteria);

        Page<TestTimingsDTO> page = testTimingsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /test-timings/count} : count all the testTimings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTestTimings(TestTimingsCriteria criteria) {
        log.debug("REST request to count TestTimings by criteria: {}", criteria);
        return ResponseEntity.ok().body(testTimingsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /test-timings/:id} : get the "id" testTimings.
     *
     * @param id the id of the testTimingsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the testTimingsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TestTimingsDTO> getTestTimings(@PathVariable("id") Long id) {
        log.debug("REST request to get TestTimings : {}", id);
        Optional<TestTimingsDTO> testTimingsDTO = testTimingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(testTimingsDTO);
    }

    /**
     * {@code DELETE  /test-timings/:id} : delete the "id" testTimings.
     *
     * @param id the id of the testTimingsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestTimings(@PathVariable("id") Long id) {
        log.debug("REST request to delete TestTimings : {}", id);
        testTimingsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
