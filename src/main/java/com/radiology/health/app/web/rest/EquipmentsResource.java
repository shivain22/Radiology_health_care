package com.radiology.health.app.web.rest;

import com.radiology.health.app.repository.EquipmentsRepository;
import com.radiology.health.app.service.EquipmentsQueryService;
import com.radiology.health.app.service.EquipmentsService;
import com.radiology.health.app.service.criteria.EquipmentsCriteria;
import com.radiology.health.app.service.dto.EquipmentsDTO;
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
 * REST controller for managing {@link com.radiology.health.app.domain.Equipments}.
 */
@RestController
@RequestMapping("/api/equipments")
public class EquipmentsResource {

    private final Logger log = LoggerFactory.getLogger(EquipmentsResource.class);

    private static final String ENTITY_NAME = "equipments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquipmentsService equipmentsService;

    private final EquipmentsRepository equipmentsRepository;

    private final EquipmentsQueryService equipmentsQueryService;

    public EquipmentsResource(
        EquipmentsService equipmentsService,
        EquipmentsRepository equipmentsRepository,
        EquipmentsQueryService equipmentsQueryService
    ) {
        this.equipmentsService = equipmentsService;
        this.equipmentsRepository = equipmentsRepository;
        this.equipmentsQueryService = equipmentsQueryService;
    }

    /**
     * {@code POST  /equipments} : Create a new equipments.
     *
     * @param equipmentsDTO the equipmentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equipmentsDTO, or with status {@code 400 (Bad Request)} if the equipments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EquipmentsDTO> createEquipments(@Valid @RequestBody EquipmentsDTO equipmentsDTO) throws URISyntaxException {
        log.debug("REST request to save Equipments : {}", equipmentsDTO);
        if (equipmentsDTO.getId() != null) {
            throw new BadRequestAlertException("A new equipments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EquipmentsDTO result = equipmentsService.save(equipmentsDTO);
        return ResponseEntity
            .created(new URI("/api/equipments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /equipments/:id} : Updates an existing equipments.
     *
     * @param id the id of the equipmentsDTO to save.
     * @param equipmentsDTO the equipmentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipmentsDTO,
     * or with status {@code 400 (Bad Request)} if the equipmentsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equipmentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EquipmentsDTO> updateEquipments(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EquipmentsDTO equipmentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Equipments : {}, {}", id, equipmentsDTO);
        if (equipmentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipmentsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipmentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EquipmentsDTO result = equipmentsService.update(equipmentsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, equipmentsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /equipments/:id} : Partial updates given fields of an existing equipments, field will ignore if it is null
     *
     * @param id the id of the equipmentsDTO to save.
     * @param equipmentsDTO the equipmentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipmentsDTO,
     * or with status {@code 400 (Bad Request)} if the equipmentsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the equipmentsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the equipmentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EquipmentsDTO> partialUpdateEquipments(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EquipmentsDTO equipmentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Equipments partially : {}, {}", id, equipmentsDTO);
        if (equipmentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipmentsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipmentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EquipmentsDTO> result = equipmentsService.partialUpdate(equipmentsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, equipmentsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /equipments} : get all the equipments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equipments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EquipmentsDTO>> getAllEquipments(
        EquipmentsCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Equipments by criteria: {}", criteria);

        Page<EquipmentsDTO> page = equipmentsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /equipments/count} : count all the equipments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEquipments(EquipmentsCriteria criteria) {
        log.debug("REST request to count Equipments by criteria: {}", criteria);
        return ResponseEntity.ok().body(equipmentsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /equipments/:id} : get the "id" equipments.
     *
     * @param id the id of the equipmentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipmentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EquipmentsDTO> getEquipments(@PathVariable("id") Long id) {
        log.debug("REST request to get Equipments : {}", id);
        Optional<EquipmentsDTO> equipmentsDTO = equipmentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(equipmentsDTO);
    }

    /**
     * {@code DELETE  /equipments/:id} : delete the "id" equipments.
     *
     * @param id the id of the equipmentsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipments(@PathVariable("id") Long id) {
        log.debug("REST request to delete Equipments : {}", id);
        equipmentsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
