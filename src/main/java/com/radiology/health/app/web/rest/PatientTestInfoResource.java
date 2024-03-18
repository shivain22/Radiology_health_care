package com.radiology.health.app.web.rest;

import com.radiology.health.app.repository.PatientTestInfoRepository;
import com.radiology.health.app.service.PatientTestInfoQueryService;
import com.radiology.health.app.service.PatientTestInfoService;
import com.radiology.health.app.service.criteria.PatientTestInfoCriteria;
import com.radiology.health.app.service.dto.PatientTestInfoDTO;
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
 * REST controller for managing {@link com.radiology.health.app.domain.PatientTestInfo}.
 */
@RestController
@RequestMapping("/api/patient-test-infos")
public class PatientTestInfoResource {

    private final Logger log = LoggerFactory.getLogger(PatientTestInfoResource.class);

    private static final String ENTITY_NAME = "patientTestInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatientTestInfoService patientTestInfoService;

    private final PatientTestInfoRepository patientTestInfoRepository;

    private final PatientTestInfoQueryService patientTestInfoQueryService;

    public PatientTestInfoResource(
        PatientTestInfoService patientTestInfoService,
        PatientTestInfoRepository patientTestInfoRepository,
        PatientTestInfoQueryService patientTestInfoQueryService
    ) {
        this.patientTestInfoService = patientTestInfoService;
        this.patientTestInfoRepository = patientTestInfoRepository;
        this.patientTestInfoQueryService = patientTestInfoQueryService;
    }

    /**
     * {@code POST  /patient-test-infos} : Create a new patientTestInfo.
     *
     * @param patientTestInfoDTO the patientTestInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patientTestInfoDTO, or with status {@code 400 (Bad Request)} if the patientTestInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PatientTestInfoDTO> createPatientTestInfo(@Valid @RequestBody PatientTestInfoDTO patientTestInfoDTO)
        throws URISyntaxException {
        log.debug("REST request to save PatientTestInfo : {}", patientTestInfoDTO);
        if (patientTestInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new patientTestInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatientTestInfoDTO result = patientTestInfoService.save(patientTestInfoDTO);
        return ResponseEntity
            .created(new URI("/api/patient-test-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /patient-test-infos/:id} : Updates an existing patientTestInfo.
     *
     * @param id the id of the patientTestInfoDTO to save.
     * @param patientTestInfoDTO the patientTestInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientTestInfoDTO,
     * or with status {@code 400 (Bad Request)} if the patientTestInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patientTestInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PatientTestInfoDTO> updatePatientTestInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PatientTestInfoDTO patientTestInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PatientTestInfo : {}, {}", id, patientTestInfoDTO);
        if (patientTestInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, patientTestInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!patientTestInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PatientTestInfoDTO result = patientTestInfoService.update(patientTestInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, patientTestInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /patient-test-infos/:id} : Partial updates given fields of an existing patientTestInfo, field will ignore if it is null
     *
     * @param id the id of the patientTestInfoDTO to save.
     * @param patientTestInfoDTO the patientTestInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientTestInfoDTO,
     * or with status {@code 400 (Bad Request)} if the patientTestInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the patientTestInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the patientTestInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PatientTestInfoDTO> partialUpdatePatientTestInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PatientTestInfoDTO patientTestInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PatientTestInfo partially : {}, {}", id, patientTestInfoDTO);
        if (patientTestInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, patientTestInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!patientTestInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PatientTestInfoDTO> result = patientTestInfoService.partialUpdate(patientTestInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, patientTestInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /patient-test-infos} : get all the patientTestInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patientTestInfos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PatientTestInfoDTO>> getAllPatientTestInfos(
        PatientTestInfoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PatientTestInfos by criteria: {}", criteria);

        Page<PatientTestInfoDTO> page = patientTestInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /patient-test-infos/count} : count all the patientTestInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPatientTestInfos(PatientTestInfoCriteria criteria) {
        log.debug("REST request to count PatientTestInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(patientTestInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /patient-test-infos/:id} : get the "id" patientTestInfo.
     *
     * @param id the id of the patientTestInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patientTestInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PatientTestInfoDTO> getPatientTestInfo(@PathVariable("id") Long id) {
        log.debug("REST request to get PatientTestInfo : {}", id);
        Optional<PatientTestInfoDTO> patientTestInfoDTO = patientTestInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patientTestInfoDTO);
    }

    /**
     * {@code DELETE  /patient-test-infos/:id} : delete the "id" patientTestInfo.
     *
     * @param id the id of the patientTestInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatientTestInfo(@PathVariable("id") Long id) {
        log.debug("REST request to delete PatientTestInfo : {}", id);
        patientTestInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
