package com.radiology.health.app.web.rest;

import com.radiology.health.app.repository.TechicianEquipmentMappingRepository;
import com.radiology.health.app.service.TechicianEquipmentMappingQueryService;
import com.radiology.health.app.service.TechicianEquipmentMappingService;
import com.radiology.health.app.service.criteria.TechicianEquipmentMappingCriteria;
import com.radiology.health.app.service.dto.TechicianEquipmentMappingDTO;
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
 * REST controller for managing {@link com.radiology.health.app.domain.TechicianEquipmentMapping}.
 */
@RestController
@RequestMapping("/api/techician-equipment-mappings")
public class TechicianEquipmentMappingResource {

    private final Logger log = LoggerFactory.getLogger(TechicianEquipmentMappingResource.class);

    private static final String ENTITY_NAME = "techicianEquipmentMapping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TechicianEquipmentMappingService techicianEquipmentMappingService;

    private final TechicianEquipmentMappingRepository techicianEquipmentMappingRepository;

    private final TechicianEquipmentMappingQueryService techicianEquipmentMappingQueryService;

    public TechicianEquipmentMappingResource(
        TechicianEquipmentMappingService techicianEquipmentMappingService,
        TechicianEquipmentMappingRepository techicianEquipmentMappingRepository,
        TechicianEquipmentMappingQueryService techicianEquipmentMappingQueryService
    ) {
        this.techicianEquipmentMappingService = techicianEquipmentMappingService;
        this.techicianEquipmentMappingRepository = techicianEquipmentMappingRepository;
        this.techicianEquipmentMappingQueryService = techicianEquipmentMappingQueryService;
    }

    /**
     * {@code POST  /techician-equipment-mappings} : Create a new techicianEquipmentMapping.
     *
     * @param techicianEquipmentMappingDTO the techicianEquipmentMappingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new techicianEquipmentMappingDTO, or with status {@code 400 (Bad Request)} if the techicianEquipmentMapping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TechicianEquipmentMappingDTO> createTechicianEquipmentMapping(
        @Valid @RequestBody TechicianEquipmentMappingDTO techicianEquipmentMappingDTO
    ) throws URISyntaxException {
        log.debug("REST request to save TechicianEquipmentMapping : {}", techicianEquipmentMappingDTO);
        if (techicianEquipmentMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new techicianEquipmentMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TechicianEquipmentMappingDTO result = techicianEquipmentMappingService.save(techicianEquipmentMappingDTO);
        return ResponseEntity
            .created(new URI("/api/techician-equipment-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /techician-equipment-mappings/:id} : Updates an existing techicianEquipmentMapping.
     *
     * @param id the id of the techicianEquipmentMappingDTO to save.
     * @param techicianEquipmentMappingDTO the techicianEquipmentMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated techicianEquipmentMappingDTO,
     * or with status {@code 400 (Bad Request)} if the techicianEquipmentMappingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the techicianEquipmentMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TechicianEquipmentMappingDTO> updateTechicianEquipmentMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TechicianEquipmentMappingDTO techicianEquipmentMappingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TechicianEquipmentMapping : {}, {}", id, techicianEquipmentMappingDTO);
        if (techicianEquipmentMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, techicianEquipmentMappingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!techicianEquipmentMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TechicianEquipmentMappingDTO result = techicianEquipmentMappingService.update(techicianEquipmentMappingDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, techicianEquipmentMappingDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /techician-equipment-mappings/:id} : Partial updates given fields of an existing techicianEquipmentMapping, field will ignore if it is null
     *
     * @param id the id of the techicianEquipmentMappingDTO to save.
     * @param techicianEquipmentMappingDTO the techicianEquipmentMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated techicianEquipmentMappingDTO,
     * or with status {@code 400 (Bad Request)} if the techicianEquipmentMappingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the techicianEquipmentMappingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the techicianEquipmentMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TechicianEquipmentMappingDTO> partialUpdateTechicianEquipmentMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TechicianEquipmentMappingDTO techicianEquipmentMappingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TechicianEquipmentMapping partially : {}, {}", id, techicianEquipmentMappingDTO);
        if (techicianEquipmentMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, techicianEquipmentMappingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!techicianEquipmentMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TechicianEquipmentMappingDTO> result = techicianEquipmentMappingService.partialUpdate(techicianEquipmentMappingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, techicianEquipmentMappingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /techician-equipment-mappings} : get all the techicianEquipmentMappings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of techicianEquipmentMappings in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TechicianEquipmentMappingDTO>> getAllTechicianEquipmentMappings(
        TechicianEquipmentMappingCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TechicianEquipmentMappings by criteria: {}", criteria);

        Page<TechicianEquipmentMappingDTO> page = techicianEquipmentMappingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /techician-equipment-mappings/count} : count all the techicianEquipmentMappings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTechicianEquipmentMappings(TechicianEquipmentMappingCriteria criteria) {
        log.debug("REST request to count TechicianEquipmentMappings by criteria: {}", criteria);
        return ResponseEntity.ok().body(techicianEquipmentMappingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /techician-equipment-mappings/:id} : get the "id" techicianEquipmentMapping.
     *
     * @param id the id of the techicianEquipmentMappingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the techicianEquipmentMappingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TechicianEquipmentMappingDTO> getTechicianEquipmentMapping(@PathVariable("id") Long id) {
        log.debug("REST request to get TechicianEquipmentMapping : {}", id);
        Optional<TechicianEquipmentMappingDTO> techicianEquipmentMappingDTO = techicianEquipmentMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(techicianEquipmentMappingDTO);
    }

    /**
     * {@code DELETE  /techician-equipment-mappings/:id} : delete the "id" techicianEquipmentMapping.
     *
     * @param id the id of the techicianEquipmentMappingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechicianEquipmentMapping(@PathVariable("id") Long id) {
        log.debug("REST request to delete TechicianEquipmentMapping : {}", id);
        techicianEquipmentMappingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
