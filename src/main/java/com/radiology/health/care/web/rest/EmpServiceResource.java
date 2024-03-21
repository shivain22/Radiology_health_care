package com.radiology.health.care.web.rest;

import com.radiology.health.care.repository.EmpServiceRepository;
import com.radiology.health.care.service.EmpServiceQueryService;
import com.radiology.health.care.service.EmpServiceService;
import com.radiology.health.care.service.criteria.EmpServiceCriteria;
import com.radiology.health.care.service.dto.EmpServiceDTO;
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
 * REST controller for managing {@link com.radiology.health.care.domain.EmpService}.
 */
@RestController
@RequestMapping("/api/emp-services")
public class EmpServiceResource {

    private final Logger log = LoggerFactory.getLogger(EmpServiceResource.class);

    private static final String ENTITY_NAME = "empService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmpServiceService empServiceService;

    private final EmpServiceRepository empServiceRepository;

    private final EmpServiceQueryService empServiceQueryService;

    public EmpServiceResource(
        EmpServiceService empServiceService,
        EmpServiceRepository empServiceRepository,
        EmpServiceQueryService empServiceQueryService
    ) {
        this.empServiceService = empServiceService;
        this.empServiceRepository = empServiceRepository;
        this.empServiceQueryService = empServiceQueryService;
    }

    /**
     * {@code POST  /emp-services} : Create a new empService.
     *
     * @param empServiceDTO the empServiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new empServiceDTO, or with status {@code 400 (Bad Request)} if the empService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EmpServiceDTO> createEmpService(@Valid @RequestBody EmpServiceDTO empServiceDTO) throws URISyntaxException {
        log.debug("REST request to save EmpService : {}", empServiceDTO);
        if (empServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new empService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmpServiceDTO result = empServiceService.save(empServiceDTO);
        return ResponseEntity
            .created(new URI("/api/emp-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emp-services/:id} : Updates an existing empService.
     *
     * @param id the id of the empServiceDTO to save.
     * @param empServiceDTO the empServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empServiceDTO,
     * or with status {@code 400 (Bad Request)} if the empServiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the empServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmpServiceDTO> updateEmpService(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmpServiceDTO empServiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EmpService : {}, {}", id, empServiceDTO);
        if (empServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empServiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmpServiceDTO result = empServiceService.update(empServiceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, empServiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /emp-services/:id} : Partial updates given fields of an existing empService, field will ignore if it is null
     *
     * @param id the id of the empServiceDTO to save.
     * @param empServiceDTO the empServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empServiceDTO,
     * or with status {@code 400 (Bad Request)} if the empServiceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the empServiceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the empServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmpServiceDTO> partialUpdateEmpService(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmpServiceDTO empServiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmpService partially : {}, {}", id, empServiceDTO);
        if (empServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empServiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmpServiceDTO> result = empServiceService.partialUpdate(empServiceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, empServiceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /emp-services} : get all the empServices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of empServices in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EmpServiceDTO>> getAllEmpServices(
        EmpServiceCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmpServices by criteria: {}", criteria);

        Page<EmpServiceDTO> page = empServiceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /emp-services/count} : count all the empServices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEmpServices(EmpServiceCriteria criteria) {
        log.debug("REST request to count EmpServices by criteria: {}", criteria);
        return ResponseEntity.ok().body(empServiceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /emp-services/:id} : get the "id" empService.
     *
     * @param id the id of the empServiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the empServiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmpServiceDTO> getEmpService(@PathVariable("id") Long id) {
        log.debug("REST request to get EmpService : {}", id);
        Optional<EmpServiceDTO> empServiceDTO = empServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(empServiceDTO);
    }

    /**
     * {@code DELETE  /emp-services/:id} : delete the "id" empService.
     *
     * @param id the id of the empServiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpService(@PathVariable("id") Long id) {
        log.debug("REST request to delete EmpService : {}", id);
        empServiceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
