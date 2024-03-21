package com.radiology.health.care.web.rest;

import com.radiology.health.care.repository.TechnicianEquipmentMappingRepository;
import com.radiology.health.care.service.TechnicianEquipmentMappingQueryService;
import com.radiology.health.care.service.TechnicianEquipmentMappingService;
import com.radiology.health.care.service.criteria.TechnicianEquipmentMappingCriteria;
import com.radiology.health.care.service.dto.TechnicianEquipmentMappingDTO;
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
 * REST controller for managing {@link com.radiology.health.care.domain.TechnicianEquipmentMapping}.
 */
@RestController
@RequestMapping("/api/technician-equipment-mappings")
public class TechnicianEquipmentMappingResource {

    private final Logger log = LoggerFactory.getLogger(TechnicianEquipmentMappingResource.class);

    private static final String ENTITY_NAME = "technicianEquipmentMapping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TechnicianEquipmentMappingService technicianEquipmentMappingService;

    private final TechnicianEquipmentMappingRepository technicianEquipmentMappingRepository;

    private final TechnicianEquipmentMappingQueryService technicianEquipmentMappingQueryService;

    public TechnicianEquipmentMappingResource(
        TechnicianEquipmentMappingService technicianEquipmentMappingService,
        TechnicianEquipmentMappingRepository technicianEquipmentMappingRepository,
        TechnicianEquipmentMappingQueryService technicianEquipmentMappingQueryService
    ) {
        this.technicianEquipmentMappingService = technicianEquipmentMappingService;
        this.technicianEquipmentMappingRepository = technicianEquipmentMappingRepository;
        this.technicianEquipmentMappingQueryService = technicianEquipmentMappingQueryService;
    }

    /**
     * {@code POST  /technician-equipment-mappings} : Create a new technicianEquipmentMapping.
     *
     * @param technicianEquipmentMappingDTO the technicianEquipmentMappingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new technicianEquipmentMappingDTO, or with status {@code 400 (Bad Request)} if the technicianEquipmentMapping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TechnicianEquipmentMappingDTO> createTechnicianEquipmentMapping(
        @Valid @RequestBody TechnicianEquipmentMappingDTO technicianEquipmentMappingDTO
    ) throws URISyntaxException {
        log.debug("REST request to save TechnicianEquipmentMapping : {}", technicianEquipmentMappingDTO);
        if (technicianEquipmentMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new technicianEquipmentMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TechnicianEquipmentMappingDTO result = technicianEquipmentMappingService.save(technicianEquipmentMappingDTO);
        return ResponseEntity
            .created(new URI("/api/technician-equipment-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /technician-equipment-mappings/:id} : Updates an existing technicianEquipmentMapping.
     *
     * @param id the id of the technicianEquipmentMappingDTO to save.
     * @param technicianEquipmentMappingDTO the technicianEquipmentMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated technicianEquipmentMappingDTO,
     * or with status {@code 400 (Bad Request)} if the technicianEquipmentMappingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the technicianEquipmentMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TechnicianEquipmentMappingDTO> updateTechnicianEquipmentMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TechnicianEquipmentMappingDTO technicianEquipmentMappingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TechnicianEquipmentMapping : {}, {}", id, technicianEquipmentMappingDTO);
        if (technicianEquipmentMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, technicianEquipmentMappingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!technicianEquipmentMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TechnicianEquipmentMappingDTO result = technicianEquipmentMappingService.update(technicianEquipmentMappingDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, technicianEquipmentMappingDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /technician-equipment-mappings/:id} : Partial updates given fields of an existing technicianEquipmentMapping, field will ignore if it is null
     *
     * @param id the id of the technicianEquipmentMappingDTO to save.
     * @param technicianEquipmentMappingDTO the technicianEquipmentMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated technicianEquipmentMappingDTO,
     * or with status {@code 400 (Bad Request)} if the technicianEquipmentMappingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the technicianEquipmentMappingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the technicianEquipmentMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TechnicianEquipmentMappingDTO> partialUpdateTechnicianEquipmentMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TechnicianEquipmentMappingDTO technicianEquipmentMappingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TechnicianEquipmentMapping partially : {}, {}", id, technicianEquipmentMappingDTO);
        if (technicianEquipmentMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, technicianEquipmentMappingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!technicianEquipmentMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TechnicianEquipmentMappingDTO> result = technicianEquipmentMappingService.partialUpdate(technicianEquipmentMappingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, technicianEquipmentMappingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /technician-equipment-mappings} : get all the technicianEquipmentMappings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of technicianEquipmentMappings in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TechnicianEquipmentMappingDTO>> getAllTechnicianEquipmentMappings(
        TechnicianEquipmentMappingCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TechnicianEquipmentMappings by criteria: {}", criteria);

        Page<TechnicianEquipmentMappingDTO> page = technicianEquipmentMappingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /technician-equipment-mappings/count} : count all the technicianEquipmentMappings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTechnicianEquipmentMappings(TechnicianEquipmentMappingCriteria criteria) {
        log.debug("REST request to count TechnicianEquipmentMappings by criteria: {}", criteria);
        return ResponseEntity.ok().body(technicianEquipmentMappingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /technician-equipment-mappings/:id} : get the "id" technicianEquipmentMapping.
     *
     * @param id the id of the technicianEquipmentMappingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the technicianEquipmentMappingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TechnicianEquipmentMappingDTO> getTechnicianEquipmentMapping(@PathVariable("id") Long id) {
        log.debug("REST request to get TechnicianEquipmentMapping : {}", id);
        Optional<TechnicianEquipmentMappingDTO> technicianEquipmentMappingDTO = technicianEquipmentMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(technicianEquipmentMappingDTO);
    }

    /**
     * {@code DELETE  /technician-equipment-mappings/:id} : delete the "id" technicianEquipmentMapping.
     *
     * @param id the id of the technicianEquipmentMappingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechnicianEquipmentMapping(@PathVariable("id") Long id) {
        log.debug("REST request to delete TechnicianEquipmentMapping : {}", id);
        technicianEquipmentMappingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
