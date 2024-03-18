package com.radiology.health.app.web.rest;

import com.radiology.health.app.repository.RoomsRepository;
import com.radiology.health.app.service.RoomsQueryService;
import com.radiology.health.app.service.RoomsService;
import com.radiology.health.app.service.criteria.RoomsCriteria;
import com.radiology.health.app.service.dto.RoomsDTO;
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
 * REST controller for managing {@link com.radiology.health.app.domain.Rooms}.
 */
@RestController
@RequestMapping("/api/rooms")
public class RoomsResource {

    private final Logger log = LoggerFactory.getLogger(RoomsResource.class);

    private static final String ENTITY_NAME = "rooms";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoomsService roomsService;

    private final RoomsRepository roomsRepository;

    private final RoomsQueryService roomsQueryService;

    public RoomsResource(RoomsService roomsService, RoomsRepository roomsRepository, RoomsQueryService roomsQueryService) {
        this.roomsService = roomsService;
        this.roomsRepository = roomsRepository;
        this.roomsQueryService = roomsQueryService;
    }

    /**
     * {@code POST  /rooms} : Create a new rooms.
     *
     * @param roomsDTO the roomsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roomsDTO, or with status {@code 400 (Bad Request)} if the rooms has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RoomsDTO> createRooms(@Valid @RequestBody RoomsDTO roomsDTO) throws URISyntaxException {
        log.debug("REST request to save Rooms : {}", roomsDTO);
        if (roomsDTO.getId() != null) {
            throw new BadRequestAlertException("A new rooms cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoomsDTO result = roomsService.save(roomsDTO);
        return ResponseEntity
            .created(new URI("/api/rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rooms/:id} : Updates an existing rooms.
     *
     * @param id the id of the roomsDTO to save.
     * @param roomsDTO the roomsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roomsDTO,
     * or with status {@code 400 (Bad Request)} if the roomsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roomsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RoomsDTO> updateRooms(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RoomsDTO roomsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Rooms : {}, {}", id, roomsDTO);
        if (roomsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roomsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roomsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RoomsDTO result = roomsService.update(roomsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, roomsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rooms/:id} : Partial updates given fields of an existing rooms, field will ignore if it is null
     *
     * @param id the id of the roomsDTO to save.
     * @param roomsDTO the roomsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roomsDTO,
     * or with status {@code 400 (Bad Request)} if the roomsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the roomsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the roomsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RoomsDTO> partialUpdateRooms(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RoomsDTO roomsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rooms partially : {}, {}", id, roomsDTO);
        if (roomsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roomsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roomsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RoomsDTO> result = roomsService.partialUpdate(roomsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, roomsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /rooms} : get all the rooms.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rooms in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RoomsDTO>> getAllRooms(
        RoomsCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Rooms by criteria: {}", criteria);

        Page<RoomsDTO> page = roomsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rooms/count} : count all the rooms.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countRooms(RoomsCriteria criteria) {
        log.debug("REST request to count Rooms by criteria: {}", criteria);
        return ResponseEntity.ok().body(roomsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /rooms/:id} : get the "id" rooms.
     *
     * @param id the id of the roomsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roomsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RoomsDTO> getRooms(@PathVariable("id") Long id) {
        log.debug("REST request to get Rooms : {}", id);
        Optional<RoomsDTO> roomsDTO = roomsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roomsDTO);
    }

    /**
     * {@code DELETE  /rooms/:id} : delete the "id" rooms.
     *
     * @param id the id of the roomsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRooms(@PathVariable("id") Long id) {
        log.debug("REST request to delete Rooms : {}", id);
        roomsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
