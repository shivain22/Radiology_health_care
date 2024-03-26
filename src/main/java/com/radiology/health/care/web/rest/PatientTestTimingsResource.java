package com.radiology.health.care.web.rest;

import com.radiology.health.care.repository.PatientTestTimingsRepository;
import com.radiology.health.care.service.PatientTestTimingsQueryService;
import com.radiology.health.care.service.PatientTestTimingsService;
import com.radiology.health.care.service.criteria.PatientTestTimingsCriteria;
import com.radiology.health.care.service.dto.PatientTestTimingsDTO;
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
 * REST controller for managing {@link com.radiology.health.care.domain.PatientTestTimings}.
 */
@RestController
@RequestMapping("/api/patient-test-timings")
public class PatientTestTimingsResource {

    private final Logger log = LoggerFactory.getLogger(PatientTestTimingsResource.class);

    private static final String ENTITY_NAME = "patientTestTimings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatientTestTimingsService patientTestTimingsService;

    private final PatientTestTimingsRepository patientTestTimingsRepository;

    private final PatientTestTimingsQueryService patientTestTimingsQueryService;

    public PatientTestTimingsResource(
        PatientTestTimingsService patientTestTimingsService,
        PatientTestTimingsRepository patientTestTimingsRepository,
        PatientTestTimingsQueryService patientTestTimingsQueryService
    ) {
        this.patientTestTimingsService = patientTestTimingsService;
        this.patientTestTimingsRepository = patientTestTimingsRepository;
        this.patientTestTimingsQueryService = patientTestTimingsQueryService;
    }

    /**
     * {@code POST  /patient-test-timings} : Create a new patientTestTimings.
     *
     * @param patientTestTimingsDTO the patientTestTimingsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patientTestTimingsDTO, or with status {@code 400 (Bad Request)} if the patientTestTimings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PatientTestTimingsDTO> createPatientTestTimings(@Valid @RequestBody PatientTestTimingsDTO patientTestTimingsDTO)
        throws URISyntaxException {
        log.debug("REST request to save PatientTestTimings : {}", patientTestTimingsDTO);
        if (patientTestTimingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new patientTestTimings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatientTestTimingsDTO result = patientTestTimingsService.save(patientTestTimingsDTO);
        return ResponseEntity
            .created(new URI("/api/patient-test-timings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /patient-test-timings/:id} : Updates an existing patientTestTimings.
     *
     * @param id the id of the patientTestTimingsDTO to save.
     * @param patientTestTimingsDTO the patientTestTimingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientTestTimingsDTO,
     * or with status {@code 400 (Bad Request)} if the patientTestTimingsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patientTestTimingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PatientTestTimingsDTO> updatePatientTestTimings(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PatientTestTimingsDTO patientTestTimingsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PatientTestTimings : {}, {}", id, patientTestTimingsDTO);
        if (patientTestTimingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, patientTestTimingsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!patientTestTimingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PatientTestTimingsDTO result = patientTestTimingsService.update(patientTestTimingsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, patientTestTimingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /patient-test-timings/:id} : Partial updates given fields of an existing patientTestTimings, field will ignore if it is null
     *
     * @param id the id of the patientTestTimingsDTO to save.
     * @param patientTestTimingsDTO the patientTestTimingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientTestTimingsDTO,
     * or with status {@code 400 (Bad Request)} if the patientTestTimingsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the patientTestTimingsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the patientTestTimingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PatientTestTimingsDTO> partialUpdatePatientTestTimings(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PatientTestTimingsDTO patientTestTimingsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PatientTestTimings partially : {}, {}", id, patientTestTimingsDTO);
        if (patientTestTimingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, patientTestTimingsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!patientTestTimingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PatientTestTimingsDTO> result = patientTestTimingsService.partialUpdate(patientTestTimingsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, patientTestTimingsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /patient-test-timings} : get all the patientTestTimings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patientTestTimings in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PatientTestTimingsDTO>> getAllPatientTestTimings(
        PatientTestTimingsCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PatientTestTimings by criteria: {}", criteria);

        Page<PatientTestTimingsDTO> page = patientTestTimingsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /patient-test-timings/count} : count all the patientTestTimings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPatientTestTimings(PatientTestTimingsCriteria criteria) {
        log.debug("REST request to count PatientTestTimings by criteria: {}", criteria);
        return ResponseEntity.ok().body(patientTestTimingsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /patient-test-timings/:id} : get the "id" patientTestTimings.
     *
     * @param id the id of the patientTestTimingsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patientTestTimingsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PatientTestTimingsDTO> getPatientTestTimings(@PathVariable("id") Long id) {
        log.debug("REST request to get PatientTestTimings : {}", id);
        Optional<PatientTestTimingsDTO> patientTestTimingsDTO = patientTestTimingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patientTestTimingsDTO);
    }

    /**
     * {@code DELETE  /patient-test-timings/:id} : delete the "id" patientTestTimings.
     *
     * @param id the id of the patientTestTimingsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatientTestTimings(@PathVariable("id") Long id) {
        log.debug("REST request to delete PatientTestTimings : {}", id);
        patientTestTimingsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
