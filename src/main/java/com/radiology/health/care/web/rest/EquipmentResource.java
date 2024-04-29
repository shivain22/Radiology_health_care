package com.radiology.health.care.web.rest;

import com.radiology.health.care.repository.EquipmentRepository;
import com.radiology.health.care.service.EquipmentQueryService;
import com.radiology.health.care.service.EquipmentService;
import com.radiology.health.care.service.criteria.EquipmentCriteria;
import com.radiology.health.care.service.dto.EquipmentDTO;
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
 * REST controller for managing {@link com.radiology.health.care.domain.Equipment}.
 */
@RestController
@RequestMapping("/api/equipment")
public class EquipmentResource {

    private final Logger log = LoggerFactory.getLogger(EquipmentResource.class);

    private static final String ENTITY_NAME = "equipment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquipmentService equipmentService;

    private final EquipmentRepository equipmentRepository;

    private final EquipmentQueryService equipmentQueryService;

    public EquipmentResource(
        EquipmentService equipmentService,
        EquipmentRepository equipmentRepository,
        EquipmentQueryService equipmentQueryService
    ) {
        this.equipmentService = equipmentService;
        this.equipmentRepository = equipmentRepository;
        this.equipmentQueryService = equipmentQueryService;
    }

    /**
     * {@code POST  /equipment} : Create a new equipment.
     *
     * @param equipmentDTO the equipmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equipmentDTO, or with status {@code 400 (Bad Request)} if the equipment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EquipmentDTO> createEquipment(@Valid @RequestBody EquipmentDTO equipmentDTO) throws URISyntaxException {
        log.debug("REST request to save Equipment : {}", equipmentDTO);
        if (equipmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new equipment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EquipmentDTO result = equipmentService.save(equipmentDTO);
        return ResponseEntity
            .created(new URI("/api/equipment/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /equipment/:id} : Updates an existing equipment.
     *
     * @param id the id of the equipmentDTO to save.
     * @param equipmentDTO the equipmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipmentDTO,
     * or with status {@code 400 (Bad Request)} if the equipmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equipmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EquipmentDTO> updateEquipment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EquipmentDTO equipmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Equipment : {}, {}", id, equipmentDTO);
        if (equipmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EquipmentDTO result = equipmentService.update(equipmentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, equipmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /equipment/:id} : Partial updates given fields of an existing equipment, field will ignore if it is null
     *
     * @param id the id of the equipmentDTO to save.
     * @param equipmentDTO the equipmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipmentDTO,
     * or with status {@code 400 (Bad Request)} if the equipmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the equipmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the equipmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EquipmentDTO> partialUpdateEquipment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EquipmentDTO equipmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Equipment partially : {}, {}", id, equipmentDTO);
        if (equipmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EquipmentDTO> result = equipmentService.partialUpdate(equipmentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, equipmentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /equipment} : get all the equipment.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equipment in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EquipmentDTO>> getAllEquipment(
        EquipmentCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Equipment by criteria: {}", criteria);
        pageable = Pageable.unpaged();
        Page<EquipmentDTO> page = equipmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /equipment/count} : count all the equipment.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEquipment(EquipmentCriteria criteria) {
        log.debug("REST request to count Equipment by criteria: {}", criteria);
        return ResponseEntity.ok().body(equipmentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /equipment/:id} : get the "id" equipment.
     *
     * @param id the id of the equipmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EquipmentDTO> getEquipment(@PathVariable("id") Long id) {
        log.debug("REST request to get Equipment : {}", id);
        Optional<EquipmentDTO> equipmentDTO = equipmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(equipmentDTO);
    }

    /**
     * {@code DELETE  /equipment/:id} : delete the "id" equipment.
     *
     * @param id the id of the equipmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable("id") Long id) {
        log.debug("REST request to delete Equipment : {}", id);
        equipmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
