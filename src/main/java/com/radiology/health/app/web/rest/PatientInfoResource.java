package com.radiology.health.app.web.rest;

import com.radiology.health.app.repository.PatientInfoRepository;
import com.radiology.health.app.service.PatientInfoQueryService;
import com.radiology.health.app.service.PatientInfoService;
import com.radiology.health.app.service.criteria.PatientInfoCriteria;
import com.radiology.health.app.service.dto.PatientInfoDTO;
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
 * REST controller for managing {@link com.radiology.health.app.domain.PatientInfo}.
 */
@RestController
@RequestMapping("/api/patient-infos")
public class PatientInfoResource {

    private final Logger log = LoggerFactory.getLogger(PatientInfoResource.class);

    private static final String ENTITY_NAME = "patientInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatientInfoService patientInfoService;

    private final PatientInfoRepository patientInfoRepository;

    private final PatientInfoQueryService patientInfoQueryService;

    public PatientInfoResource(
        PatientInfoService patientInfoService,
        PatientInfoRepository patientInfoRepository,
        PatientInfoQueryService patientInfoQueryService
    ) {
        this.patientInfoService = patientInfoService;
        this.patientInfoRepository = patientInfoRepository;
        this.patientInfoQueryService = patientInfoQueryService;
    }

    /**
     * {@code POST  /patient-infos} : Create a new patientInfo.
     *
     * @param patientInfoDTO the patientInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patientInfoDTO, or with status {@code 400 (Bad Request)} if the patientInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PatientInfoDTO> createPatientInfo(@Valid @RequestBody PatientInfoDTO patientInfoDTO) throws URISyntaxException {
        log.debug("REST request to save PatientInfo : {}", patientInfoDTO);
        if (patientInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new patientInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatientInfoDTO result = patientInfoService.save(patientInfoDTO);
        return ResponseEntity
            .created(new URI("/api/patient-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /patient-infos/:id} : Updates an existing patientInfo.
     *
     * @param id the id of the patientInfoDTO to save.
     * @param patientInfoDTO the patientInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientInfoDTO,
     * or with status {@code 400 (Bad Request)} if the patientInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patientInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PatientInfoDTO> updatePatientInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PatientInfoDTO patientInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PatientInfo : {}, {}", id, patientInfoDTO);
        if (patientInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, patientInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!patientInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PatientInfoDTO result = patientInfoService.update(patientInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, patientInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /patient-infos/:id} : Partial updates given fields of an existing patientInfo, field will ignore if it is null
     *
     * @param id the id of the patientInfoDTO to save.
     * @param patientInfoDTO the patientInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientInfoDTO,
     * or with status {@code 400 (Bad Request)} if the patientInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the patientInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the patientInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PatientInfoDTO> partialUpdatePatientInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PatientInfoDTO patientInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PatientInfo partially : {}, {}", id, patientInfoDTO);
        if (patientInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, patientInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!patientInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PatientInfoDTO> result = patientInfoService.partialUpdate(patientInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, patientInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /patient-infos} : get all the patientInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patientInfos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PatientInfoDTO>> getAllPatientInfos(
        PatientInfoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PatientInfos by criteria: {}", criteria);

        Page<PatientInfoDTO> page = patientInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /patient-infos/count} : count all the patientInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPatientInfos(PatientInfoCriteria criteria) {
        log.debug("REST request to count PatientInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(patientInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /patient-infos/:id} : get the "id" patientInfo.
     *
     * @param id the id of the patientInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patientInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PatientInfoDTO> getPatientInfo(@PathVariable("id") Long id) {
        log.debug("REST request to get PatientInfo : {}", id);
        Optional<PatientInfoDTO> patientInfoDTO = patientInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patientInfoDTO);
    }

    /**
     * {@code DELETE  /patient-infos/:id} : delete the "id" patientInfo.
     *
     * @param id the id of the patientInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatientInfo(@PathVariable("id") Long id) {
        log.debug("REST request to delete PatientInfo : {}", id);
        patientInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
